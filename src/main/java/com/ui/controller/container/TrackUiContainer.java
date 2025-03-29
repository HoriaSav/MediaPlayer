package com.ui.controller.container;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class TrackUiContainer {

    private Slider songSlider;
    private Slider volumeSlider;

    private Label songDurationLabel;
    private Label trackNameLabel;
    private Label albumNameLabel;
    private Label artistLabel;
    private Label volumeLabel;

    private Button playPauseButton;
    private boolean isMuted = false;

    public TrackUiContainer(Slider songSlider, Slider volumeSlider, Label volumeLabel, Label songDurationLabel, Label trackNameLabel, Label albumNameLabel, Label artistLabel) {
        this.songSlider = songSlider;
        this.volumeSlider = volumeSlider;
        this.songDurationLabel = songDurationLabel;
        this.trackNameLabel = trackNameLabel;
        this.albumNameLabel = albumNameLabel;
        this.artistLabel = artistLabel;
        this.volumeLabel = volumeLabel;
    }

    public Slider getSongSlider() {
        return songSlider;
    }

    public void setSongSlider(Slider songSlider) {
        this.songSlider = songSlider;
    }

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public void setVolumeSlider(Slider volumeSlider) {
        this.volumeSlider = volumeSlider;
    }

    public Label getSongDurationLabel() {
        return songDurationLabel;
    }

    public void setSongDurationLabel(Label songDurationLabel) {
        this.songDurationLabel = songDurationLabel;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public Label getAlbumNameLabel() {
        return albumNameLabel;
    }

    public void setAlbumNameLabel(Label albumName) {
        this.albumNameLabel = albumName;
    }

    public Label getTrackNameLabel() {
        return trackNameLabel;
    }

    public void setTrackNameLabel(Label trackName) {
        this.trackNameLabel = trackName;
    }

    public Label getArtistLabel() {
        return artistLabel;
    }

    public void setArtistLabel(Label artistLabel) {
        this.artistLabel = artistLabel;
    }

    public Label getVolumeLabel() {
        return volumeLabel;
    }

    public void setVolumeLabel(Label volumeLabel) {
        this.volumeLabel = volumeLabel;
    }

    public Button getPlayPauseButton() {
        return playPauseButton;
    }

    public void setPlayPauseButton(Button playPauseButton) {
        this.playPauseButton = playPauseButton;
    }
}
