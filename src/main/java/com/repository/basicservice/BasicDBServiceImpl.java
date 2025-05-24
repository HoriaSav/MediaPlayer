package com.repository.basicservice;

import com.repository.PersistentObject;
import com.repository.basicservice.impl.*;
import com.repository.basicservice.interfaces.*;
import com.repository.exception.FetchException;
import com.repository.exception.ServiceException;
import com.repository.exception.StoreException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the BasicDBService interface for database operations.
 */

public class BasicDBServiceImpl implements BasicDBService {
    private final Connection connection;

    public BasicDBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Artist createArtist(String name, String genre) {
        return new ArtistImpl(this, name, genre);
    }

    @Override
    public Album createAlbum(String name, Artist artist) {
        return new AlbumImpl(this, name, artist);
    }

    @Override
    public Playlist createPlaylist(String name) {
        return new PlaylistImpl(this, name);
    }

    @Override
    public Track createTrack(String name, int durationSec, String path, Album album) {
        return new TrackImpl(this, name, durationSec, path, album);
    }

    @Override
    public PlaylistTrack createPlaylistTrack(Playlist playlist, Track track, int trackPlaylistNumber) {
        return new PlaylistTrackImpl(this, playlist, track, trackPlaylistNumber);
    }

    @Override
    public List<Artist> getArtists(String genre) {
        List<Artist> artists = new ArrayList<>();
        String sql;
        if (genre.isEmpty()) {
            sql = "SELECT * FROM artist";
        } else {
            sql = "SELECT * FROM artist WHERE genre = ?";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArtistImpl artist = new ArtistImpl(this, rs.getLong("id"));
                artist.setName(rs.getString("name"));
                artist.setGenre(rs.getString("genre"));
                artists.add(artist);
            }
        } catch (SQLException e) {
            throw new FetchException("Failed to fetch Artists", e);
        }
        return artists;
    }

    @Override
    public Artist getArtist(long id) {
        if (id <= 0) {
            throw new AssertionError("Invalid ID provided: ID must be positive.");
        }
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM artist WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ArtistImpl artist = new ArtistImpl(this, id);
                artist.setName(rs.getString("name"));
                artist.setGenre(rs.getString("genre"));
                return artist;
            } else {
                return null; // No artist with that ID
            }
        } catch (SQLException e) {
            throw new FetchException("Failed to fetch Artist", e);
        }
    }

    @Override
    public List<Album> getAlbums(String artistName) {
        List<Album> albums = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM album where name = ?")) {
            ps.setString(1, artistName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AlbumImpl album = new AlbumImpl(this, rs.getLong("id"));
                album.setName(rs.getString("name"));
                album.setArtist(getArtist(rs.getLong("artist_id")));
                albums.add(album);
            }
        } catch (SQLException e) {
            throw new FetchException("Failed to get albums for artist " + artistName + " :");
        }
        return albums;
    }

    @Override
    public Album getAlbum(long id) {
        if (id <= 0) {
            throw new AssertionError("Invalid ID provided: ID must be positive.");
        }
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM album WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AlbumImpl album = new AlbumImpl(this, id);
                album.setName(rs.getString("name"));
                album.setArtist(getArtist(rs.getLong("artist_id")));
                return album;
            } else {
                return null; // No album with that ID
            }
        } catch (SQLException e) {
            throw new FetchException("Failed to fetch Artist", e);
        }
    }

    @Override
    public List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM playlist")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PlaylistImpl playlist = new PlaylistImpl(this, rs.getLong("id"));
                playlist.setName(rs.getString("name"));
                playlist.setCreationDate(rs.getDate("creation_date").toLocalDate());
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            throw new FetchException("Failed to get playlists");
        }
        return playlists;
    }

    private void getTracksPs(List<Track> tracks, PreparedStatement ps, long objectID) throws SQLException {
        ps.setLong(1, objectID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            TrackImpl track = new TrackImpl(this, rs.getLong("id"));
            track.setName(rs.getString("name"));
            track.setAlbum(getAlbum(rs.getLong("album")));
            track.setDurationSec(rs.getInt("duration"));
            track.setPath(rs.getString("path"));
            track.setIsFavorite(rs.getBoolean("isfavorite"));
            tracks.add(track);
        }
    }

    @Override
    public List<Track> getPlaylistTracks(Playlist playlist) {
        List<Track> tracks = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("")) {
            getTracksPs(tracks, ps, playlist.getObjectID());
        } catch (SQLException e) {
            throw new FetchException("Failed to get tracks for playlist " + playlist.getName());
        }
        return tracks;
    }

    @Override
    public List<Track> getAlbumTracks(Album album) {
        List<Track> tracks = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM track WHERE album_id = ?")) {
            getTracksPs(tracks, ps, album.getObjectID());
        } catch (SQLException e) {
            throw new FetchException("Failed to get tracks for album " + album.getName());
        }
        return tracks;
    }

    @Override
    public List<Track> getArtistTracks(Artist artist) {
        List<Track> tracks = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM track t, album al, artist ar where al.artist_id = ar.id and t.album_id = al.id and ar.id = ?")) {
            getTracksPs(tracks, ps, artist.getObjectID());
        } catch (SQLException e) {
            throw new FetchException("Failed to get tracks for artist " + artist.getName());
        }
        return tracks;
    }

    @Override
    public List<Track> getLikedTracks() {
        List<Track> tracks = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM track where isfavorite = ?")) {
            ps.setBoolean(1, true);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TrackImpl track = new TrackImpl(this, rs.getLong("id"));
                track.setName(rs.getString("name"));
                track.setDurationSec(rs.getInt("duration_sec"));
                track.setPath(rs.getString("path"));
                track.setAlbum(getAlbum(rs.getLong("album_id")));
                track.setIsFavorite(rs.getBoolean("isfavorite"));
                tracks.add(track);
            }
        } catch (SQLException e) {
            throw new FetchException("Failed to get favorite tracks");
        }
        return tracks;
    }


    @Override
    public long store(PersistentObject persistentObject) {
        if (persistentObject == null) {
            throw new AssertionError("PersistentObject must not be null");
        }
        if (!(persistentObject instanceof AbstractPersistentJDBCObject)) {
            throw new StoreException("Object is not a valid JDBC object!");
        }
        try {
            return ((AbstractPersistentJDBCObject) persistentObject).store(connection);
        } catch (SQLException e) {
            throw new StoreException("Failed to store object", e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new ServiceException("Failed to close connection", e);
        }
    }
}