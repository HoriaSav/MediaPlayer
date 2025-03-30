package com.ui.controller.container;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;

public class TrackUiContainer {

    private Slider trackSlider;
    private Slider volumeSlider;

    private Label trackDurationLabel;
    private Label trackNameLabel;
    private Label albumNameLabel;
    private Label artistLabel;
    private Label volumeLabel;

    private Button playPauseButton;
    private Button muteButton;

    private ProgressBar trackProgressBar;
    private ProgressBar volumeProgressBar;

    public TrackUiContainer() {

    }

    public Slider getTrackSlider() {
        return trackSlider;
    }

    public void setTrackSlider(Slider trackSlider) {
        this.trackSlider = trackSlider;
    }

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public void setVolumeSlider(Slider volumeSlider) {
        this.volumeSlider = volumeSlider;
    }

    public Label getTrackDurationLabel() {
        return trackDurationLabel;
    }

    public void setTrackDurationLabel(Label trackDurationLabel) {
        this.trackDurationLabel = trackDurationLabel;
    }

    public Label getTrackNameLabel() {
        return trackNameLabel;
    }

    public void setTrackNameLabel(Label trackNameLabel) {
        this.trackNameLabel = trackNameLabel;
    }

    public Label getAlbumNameLabel() {
        return albumNameLabel;
    }

    public void setAlbumNameLabel(Label albumNameLabel) {
        this.albumNameLabel = albumNameLabel;
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

    public Button getMuteButton() {
        return muteButton;
    }

    public void setMuteButton(Button muteButton) {
        this.muteButton = muteButton;
    }

    public ProgressBar getTrackProgressBar() {
        return trackProgressBar;
    }

    public void setTrackProgressBar(ProgressBar trackProgressBar) {
        this.trackProgressBar = trackProgressBar;
    }

    public ProgressBar getVolumeProgressBar() {
        return volumeProgressBar;
    }

    public void setVolumeProgressBar(ProgressBar volumeProgressBar) {
        this.volumeProgressBar = volumeProgressBar;
    }

    private void setProgressBarInSync(ProgressBar progressBar, Slider slider) {
        progressBar.progressProperty().bind(
                slider.valueProperty().divide(slider.maxProperty())
        );
    }

    public void setProgressBarsInSync(){
        setProgressBarInSync(trackProgressBar, trackSlider);
        setProgressBarInSync(volumeProgressBar, volumeSlider);
    }
}
