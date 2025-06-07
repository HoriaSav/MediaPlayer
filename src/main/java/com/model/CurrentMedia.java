package com.model;

import com.repository.basicservice.interfaces.Playlist;
import com.repository.basicservice.interfaces.Track;

import java.util.ArrayList;
import java.util.List;

public class CurrentMedia {
    private List<Track> trackList;
    private Track currentTrack;
    private Playlist currentPlaylist;

    public CurrentMedia(){
        currentPlaylist = null;
        currentTrack = null;
        trackList = new ArrayList<>();
    }

    public List<Track> getTrackList() {
        if (trackList == null) {
            throw new IllegalStateException("Track list is not initialized");
        }
        return new ArrayList<>(trackList);
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
        if (trackList == null){
            this.trackList = new ArrayList<>();
        }
        else {
            this.trackList = trackList;
            if (!this.trackList.isEmpty()) {
                currentTrack = trackList.getFirst();
            }
        }
    }

    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }
}
