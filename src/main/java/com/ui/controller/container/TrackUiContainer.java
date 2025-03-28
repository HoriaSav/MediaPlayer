package com.ui.controller.container;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class TrackUiContainer {
    private Slider songSlider;
    private Slider volumeSlider;
    private Label songDurationLabel;
    private boolean isMuted = false;
    public TrackUiContainer(Slider songSlider, Slider volumeSlider, Label songDurationLabel) {
        this.songSlider = songSlider;
        this.volumeSlider = volumeSlider;
        this.songDurationLabel = songDurationLabel;
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
}
