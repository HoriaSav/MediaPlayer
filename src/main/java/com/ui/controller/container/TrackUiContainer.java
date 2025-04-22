package com.ui.controller.container;

import com.model.Track;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.util.Duration;

/**
 * Container class that manages all UI components related to track playback and control.
 * This class serves as a centralized container for managing various JavaFX controls
 * used in the music player interface.
 */
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

    /**
     * Creates a new instance of TrackUiContainer with default values.
     */
    public TrackUiContainer() {}

    // Getter methods with JavaDoc
    /**
     * @return The slider controlling track position
     */
    public Slider getTrackSlider() {
        return trackSlider;
    }

    /**
     * @return The slider controlling volume level
     */
    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    /**
     * @return The label displaying track duration
     */
    public Label getTrackDurationLabel() {
        return trackDurationLabel;
    }

    /**
     * @return The label displaying current track name
     */
    public Label getTrackNameLabel() {
        return trackNameLabel;
    }

    /**
     * @return The label displaying album name
     */
    public Label getAlbumNameLabel() {
        return albumNameLabel;
    }

    /**
     * @return The label displaying artist name
     */
    public Label getArtistLabel() {
        return artistLabel;
    }

    /**
     * @return The label displaying volume level
     */
    public Label getVolumeLabel() {
        return volumeLabel;
    }

    /**
     * @return The button for playing/pausing track playback
     */
    public Button getPlayPauseButton() {
        return playPauseButton;
    }

    /**
     * @return The button for muting/unmuting audio
     */
    public Button getMuteButton() {
        return muteButton;
    }

    /**
     * @return The progress bar showing track position
     */
    public ProgressBar getTrackProgressBar() {
        return trackProgressBar;
    }

    /**
     * @return The progress bar showing volume level
     */
    public ProgressBar getVolumeProgressBar() {
        return volumeProgressBar;
    }

    // Setter methods with JavaDoc
    /**
     * Sets the track position slider
     * @param trackSlider The slider to control track position
     */
    public void setTrackSlider(Slider trackSlider) {
        this.trackSlider = trackSlider;
    }

    /**
     * Sets the volume control slider
     * @param volumeSlider The slider to control volume
     */
    public void setVolumeSlider(Slider volumeSlider) {
        this.volumeSlider = volumeSlider;
    }

    /**
     * Sets the track duration label
     * @param trackDurationLabel The label to display track duration
     */
    public void setTrackDurationLabel(Label trackDurationLabel) {
        this.trackDurationLabel = trackDurationLabel;
    }

    /**
     * Sets the track name label
     * @param trackNameLabel The label to display track name
     */
    public void setTrackNameLabel(Label trackNameLabel) {
        this.trackNameLabel = trackNameLabel;
    }

    /**
     * Sets the album name label
     * @param albumNameLabel The label to display album name
     */
    public void setAlbumNameLabel(Label albumNameLabel) {
        this.albumNameLabel = albumNameLabel;
    }

    /**
     * Sets the artist label
     * @param artistLabel The label to display artist name
     */
    public void setArtistLabel(Label artistLabel) {
        this.artistLabel = artistLabel;
    }

    /**
     * Sets the volume level label
     * @param volumeLabel The label to display volume level
     */
    public void setVolumeLabel(Label volumeLabel) {
        this.volumeLabel = volumeLabel;
    }

    /**
     * Sets the play/pause button
     * @param playPauseButton The button to control play/pause
     */
    public void setPlayPauseButton(Button playPauseButton) {
        this.playPauseButton = playPauseButton;
    }

    /**
     * Sets the mute button
     * @param muteButton The button to control mute/unmute
     */
    public void setMuteButton(Button muteButton) {
        this.muteButton = muteButton;
    }

    /**
     * Sets the track progress bar
     * @param trackProgressBar The progress bar showing track position
     */
    public void setTrackProgressBar(ProgressBar trackProgressBar) {
        this.trackProgressBar = trackProgressBar;
    }

    /**
     * Sets the volume progress bar
     * @param volumeProgressBar The progress bar showing volume level
     */
    public void setVolumeProgressBar(ProgressBar volumeProgressBar) {
        this.volumeProgressBar = volumeProgressBar;
    }

    /**
     * Binds a progress bar to a slider's value
     * @param progressBar The progress bar to bind
     * @param slider The slider to bind to
     */
    private void setProgressBarInSync(ProgressBar progressBar, Slider slider) {
        progressBar.progressProperty().bind(
                slider.valueProperty().divide(slider.maxProperty())
        );
    }

    /**
     * Initializes synchronization between progress bars and their corresponding sliders,
     * and sets up volume controls
     */
    public void setProgressBarsInSync() {
        setProgressBarInSync(trackProgressBar, trackSlider);
        setProgressBarInSync(volumeProgressBar, volumeSlider);
        setUpVolumeSlider();
        setUpVolumeLabel();
    }

    /**
     * Initializes the volume slider with default values
     */
    private void setUpVolumeSlider() {
        volumeSlider.setMin(0.0);
        volumeSlider.setMax(1.0);
    }

    /**
     * Sets up the volume label to display percentage values
     */
    private void setUpVolumeLabel() {
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int percent = (int) (newVal.doubleValue() * 100);
            volumeLabel.setText(percent + "%");
        });
    }

    /**
     * Updates the UI with track information from the given file
     * @param track The audio file containing track metadata
     */
    public void setTrackInfoInUI(Track track) {
        try {
            trackNameLabel.setText(track.getName());
            artistLabel.setText(track.getArtist());
        } catch (Exception e) {
            trackNameLabel.setText(track.getName());
            artistLabel.setText("");
        }
    }

    /**
     * Updates the track duration label with the given time
     * @param time The duration to display
     */
    public void setTrackDurationLabel(Duration time) {
        long minutes = (long) time.toMinutes();
        long seconds = (long) (time.toSeconds() % 60);
        trackDurationLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}