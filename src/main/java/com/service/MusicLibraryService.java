package com.service;

import com.repository.basicservice.interfaces.Album;
import com.repository.basicservice.interfaces.Artist;
import com.repository.basicservice.interfaces.Playlist;
import com.repository.basicservice.interfaces.Track;

import java.util.List;

public interface MusicLibraryService {
    void playTrack(String trackName);
    void playPause();
    void skip();
    void previous();
    void mute();

    List<Track> getCurrentTrackList();
    Track getActiveTrack();
    List<Playlist> getAllPlaylists();
    void addPlaylist();
    void setActivePlaylist(String playlist);
    Playlist getActivePlaylist();
    List<Album> getAllAlbums();
    Artist getAlbumArtist(Album album);


    void close();
}
