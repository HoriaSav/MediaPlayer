package com.model;

import com.repository.basicservice.interfaces.Playlist;
import com.repository.basicservice.interfaces.Track;

import java.util.List;

public class CurrentMedia {
    private List<Track> trackList;
    private Track currentTrack;
    private Playlist currentPlaylist;

    public CurrentMedia(){}

    public List<Track> getTrackList() {
        if (trackList == null) {
            throw new IllegalStateException("Track list is not initialized");
        }
        return trackList;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentTrack(Track currentTrack) {
        this.currentTrack = currentTrack;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
        if (trackList.isEmpty()) {
            throw new IllegalStateException("Track list is empty");
        }
        currentTrack = trackList.getFirst();
    }

    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }
}
