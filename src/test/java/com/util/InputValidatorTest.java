package com.util;

import com.repository.basicservice.interfaces.Track;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void validateTrack_ValidInput_NoException() {
        // Create a temporary file for testing
        File tempFile;
        try {
            tempFile = File.createTempFile("test", ".mp3");
            tempFile.deleteOnExit();

            // Should not throw any exception
            InputValidator.validateTrack("Test Track", "Test Artist", "Test Album", 180, tempFile.getAbsolutePath());
        } catch (IOException e) {
            fail("Failed to create temporary file");
        }
    }

    @Test
    void validateTrackName_ValidName_NoException() {
        assertDoesNotThrow(() -> InputValidator.validateTrackName("Valid Track Name"));
    }

    @Test
    void validateTrackName_NullName_ThrowsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateTrackName(null));
        assertEquals("Track name cannot be null or empty", error.getMessage());
    }

    @Test
    void validateTrackName_EmptyName_ThrowsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateTrackName("  "));
        assertEquals("Track name cannot be null or empty", error.getMessage());
    }

    @Test
    void validateTrackName_TooLongName_ThrowsAssertionError() {
        String longName = "a".repeat(256);
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateTrackName(longName));
        assertEquals("Track name exceeds maximum length (255 characters)", error.getMessage());
    }

    @Test
    void validateArtist_ValidArtist_NoException() {
        assertDoesNotThrow(() -> InputValidator.validateArtist("Valid Artist"));
    }

    @Test
    void validateArtist_EmptyArtist_NoException() {
        assertDoesNotThrow(() -> InputValidator.validateArtist(""));
    }

    @Test
    void validateArtist_NullArtist_ThrowsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateArtist(null));
        assertEquals("Artist cannot be null", error.getMessage());
    }

    @Test
    void validateArtist_TooLongArtist_ThrowsAssertionError() {
        String longArtist = "a".repeat(256);
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateArtist(longArtist));
        assertEquals("Artist name exceeds maximum length (255 characters)", error.getMessage());
    }

    @Test
    void validateAlbum_ValidAlbum_NoException() {
        assertDoesNotThrow(() -> InputValidator.validateAlbum("Valid Album"));
    }

    @Test
    void validateAlbum_EmptyAlbum_NoException() {
        assertDoesNotThrow(() -> InputValidator.validateAlbum(""));
    }

    @Test
    void validateAlbum_NullAlbum_ThrowsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateAlbum(null));
        assertEquals("Album cannot be null", error.getMessage());
    }

    @Test
    void validateAlbum_TooLongAlbum_ThrowsAssertionError() {
        String longAlbum = "a".repeat(256);
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateAlbum(longAlbum));
        assertEquals("Album name exceeds maximum length (255 characters)", error.getMessage());
    }

    @Test
    void validateDuration_ValidDuration_NoException() {
        assertDoesNotThrow(() -> InputValidator.validateDuration(180));
    }

    @Test
    void validateDuration_NegativeDuration_ThrowsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateDuration(-1));
        assertEquals("Duration cannot be negative", error.getMessage());
    }

    @Test
    void validateDuration_ExcessiveDuration_ThrowsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateDuration(43201));
        assertEquals("Duration exceeds maximum allowed length (12 hours)", error.getMessage());
    }

    @Test
    void validatePath_ValidPath_NoException(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("test.mp3").toFile();
        Files.createFile(testFile.toPath());
        assertDoesNotThrow(() -> InputValidator.validatePath(testFile.getAbsolutePath()));
    }

    @Test
    void validatePath_NullPath_ThrowsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validatePath(null));
        assertEquals("File path cannot be null or empty", error.getMessage());
    }

    @Test
    void validatePath_EmptyPath_ThrowsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validatePath("  "));
        assertEquals("File path cannot be null or empty", error.getMessage());
    }

    @Test
    void validatePath_NonexistentFile_ThrowsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validatePath("/nonexistent/file.mp3"));
        assertTrue(error.getMessage().startsWith("File does not exist:"));
    }

    @Test
    void validateFolder_ValidFolder_NoException(@TempDir Path tempDir) {
        assertDoesNotThrow(() -> InputValidator.validateFolder(tempDir.toFile()));
    }

    @Test
    void validateFolder_NullFolder_ThrowsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateFolder(null));
        assertEquals("Folder cannot be null", error.getMessage());
    }

    @Test
    void validateFolder_NonexistentFolder_ThrowsAssertionError() {
        File nonexistentFolder = new File("/nonexistent/folder");
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateFolder(nonexistentFolder));
        assertTrue(error.getMessage().startsWith("Folder does not exist:"));
    }

//    @Test
//    void validateMediaPlayerState_ValidState_NoException() {
//        Track track = new Track("Test", "Artist", "Album", 180, "path");
//        MediaPlayer mediaPlayer = new MediaPlayer(null); // Note: This is a mock situation
//        assertDoesNotThrow(() -> InputValidator.validateMediaPlayerState(mediaPlayer, track));
//    }

    @Test
    void validateMediaPlayerState_NullTrack_ThrowsAssertionError() {
        MediaPlayer mediaPlayer = new MediaPlayer(null);
        AssertionError error = assertThrows(AssertionError.class,
            () -> InputValidator.validateMediaPlayerState(mediaPlayer, null));
        assertEquals("No track selected", error.getMessage());
    }

//    @Test
//    void validateMediaPlayerState_NullMediaPlayer_ThrowsAssertionError() {
//        Track track = new Track("Test", "Artist", "Album", 180, "path");
//        AssertionError error = assertThrows(AssertionError.class,
//            () -> InputValidator.validateMediaPlayerState(null, track));
//        assertEquals("Media player not initialized", error.getMessage());
//    }
}