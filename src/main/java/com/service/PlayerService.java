package com.service;

import com.repository.basicservice.interfaces.Track;

import java.util.HashMap;

public interface PlayerService {
    void play(String trackName);
    void playNextTrack();
    void playPreviousTrack();
    void mute();

    void playPauseTrack();
}
