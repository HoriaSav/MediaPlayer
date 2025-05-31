package com.util;

import com.repository.basicservice.interfaces.Track;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Date;

public class InputValidator {
    private InputValidator() {}

    public static void validateTrack(String name, String artist, String album, int duration, String path) {
        validateTrackName(name);
        validateArtist(artist);
        validateAlbum(album);
        validateDuration(duration);
        validatePath(path);
    }

    public static void validateTrackName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new AssertionError("Track name cannot be null or empty");
        }
        if (name.length() > 255) {  // Example of a reasonable limit
            throw new AssertionError("Track name exceeds maximum length (255 characters)");
        }
    }

    public static void validateArtist(String artist) {
        if (artist == null) {
            throw new AssertionError("Artist cannot be null");
        }
        // Artist can be empty as some tracks might not have artist information
        if (artist.length() > 255) {
            throw new AssertionError("Artist name exceeds maximum length (255 characters)");
        }
    }

    public static void validateAlbum(String album) {
        if (album == null) {
            throw new AssertionError("Album cannot be null");
        }
        // Album can be empty as some tracks might not be part of an album
        if (album.length() > 255) {
            throw new AssertionError("Album name exceeds maximum length (255 characters)");
        }
    }

    public static void validateDuration(int duration) {
        if (duration < 0) {
            throw new AssertionError("Duration cannot be negative");
        }

        if (duration > 43200) {
            throw new AssertionError("Duration exceeds maximum allowed length (12 hours)");
        }
    }

    public static void validatePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new AssertionError("File path cannot be null or empty");
        }
        if (!new File(path).exists()) {
            throw new AssertionError("File does not exist: " + path);
        }
    }

    public static void validateFolder(File folder) {
        if (folder == null) {
            throw new AssertionError("Folder cannot be null");
        }
        if (!folder.exists()) {
            throw new AssertionError("Folder does not exist: " + folder.getPath());
        }
        if (!folder.isDirectory()) {
            throw new AssertionError("Path is not a directory: " + folder.getPath());
        }
        if (!folder.canRead()) {
            throw new AssertionError("Cannot read folder: " + folder.getPath());
        }
    }

    // For media player operations
    public static void validateMediaPlayerState(MediaPlayer mediaPlayer, Track currentTrack) {
        if (currentTrack == null) {
            throw new AssertionError("No track selected");
        }
        if (mediaPlayer == null) {
            throw new AssertionError("Media player not initialized");
        }
    }

    public static boolean isMusicFile(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }

        String name = file.getName().toLowerCase();
        return name.endsWith(".mp3") || name.endsWith(".wav");
    }

    public static Date getNotNullConvertedDate(java.sql.Date date) {
        if (date == null) {
            return null;
        }

        return new java.util.Date(date.getTime());
    }

    public static java.sql.Date getNotNullConvertedDate(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }
}