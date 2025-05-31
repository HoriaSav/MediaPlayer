package com.service;

import com.repository.basicservice.interfaces.Playlist;
import com.repository.basicservice.interfaces.Track;

import java.util.List;

public interface MusicLibraryService {
    void playTrack(String trackName);
    void playPause();
    void skip();
    void previous();
    void mute();

    List<Track> getTracks();
    Track getCurrentTrack();
    List<Playlist> getPlaylists();
    void addPlaylist();
    void setCurrentPlaylist(String playlist);
    Playlist getCurrentPlaylist();

    void close();


}
