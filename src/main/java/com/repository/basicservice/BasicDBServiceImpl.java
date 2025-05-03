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
        return new AlbumImpl(this, name, artist) ;
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
        if (genre.isEmpty()){
            sql = "SELECT * FROM artist";
        }
        else {
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
                return null; // No ward with that ID
            }
        } catch (SQLException e) {
            throw new FetchException("Failed to fetch Artist", e);
        }
    }

    @Override
    public List<Album> getAlbums(String artistName) {
        return List.of();
    }

    @Override
    public Album getAlbum(long id) {
        return null;
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
