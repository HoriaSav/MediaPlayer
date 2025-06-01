package com.repository.basicservice.interfaces;

import com.repository.PDBGVersion;
import com.repository.PersistentObject;

import java.util.List;

@PDBGVersion
public interface BasicDBService {
    Artist createArtist(String name, String genre);

    Album createAlbum(String name, Artist artist);

    Playlist createPlaylist(String name);

    Track createTrack(String name, int durationSec, String path, Album album);

    PlaylistTrack createPlaylistTrack(Playlist playlist, Track track, int trackPlaylistNumber);

    List<Artist> getArtists(String genre);

    Artist getArtist(long id);

    Artist getArtist(String name);

    List<Album> getAlbums(String artistName);

    Album getAlbum(long id);

    Album getAlbum(String name, Artist artist);

    List<Playlist> getPlaylists();

    Track getTrack(String name, String name1);

    List<Track> getPlaylistTracks(Playlist playlist);

    List<Track> getAlbumTracks(Album album);

    List<Track> getArtistTracks(Artist artist);

    List<Track> getLikedTracks();

    Playlist getPlaylist(String name);

    long store(PersistentObject var1);

    void close();
}
