package com.repository.util;

import com.model.Track;
import com.repository.exception.ValidationException;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class InputValidator {
    private InputValidator() {
        // Private constructor to prevent instantiation
    }

    public static void validateTrack(String name, String artist, String album, int duration, String path) {
        validateTrackName(name);
        validateArtist(artist);
        validateAlbum(album);
        validateDuration(duration);
        validatePath(path);
    }

    public static void validateTrackName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Track name cannot be null or empty");
        }
        if (name.length() > 255) {  // Example of a reasonable limit
            throw new ValidationException("Track name exceeds maximum length (255 characters)");
        }
    }

    public static void validateArtist(String artist) {
        if (artist == null) {
            throw new ValidationException("Artist cannot be null");
        }
        // Artist can be empty as some tracks might not have artist information
        if (artist.length() > 255) {
            throw new ValidationException("Artist name exceeds maximum length (255 characters)");
        }
    }

    public static void validateAlbum(String album) {
        if (album == null) {
            throw new ValidationException("Album cannot be null");
        }
        // Album can be empty as some tracks might not be part of an album
        if (album.length() > 255) {
            throw new ValidationException("Album name exceeds maximum length (255 characters)");
        }
    }

    public static void validateDuration(int duration) {
        if (duration < 0) {
            throw new ValidationException("Duration cannot be negative");
        }

        if (duration > 43200) {
            throw new ValidationException("Duration exceeds maximum allowed length (12 hours)");
        }
    }

    public static void validatePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new ValidationException("File path cannot be null or empty");
        }
        if (!new File(path).exists()) {
            throw new ValidationException("File does not exist: " + path);
        }
    }

    public static void validateFolder(File folder) {
        if (folder == null) {
            throw new ValidationException("Folder cannot be null");
        }
        if (!folder.exists()) {
            throw new ValidationException("Folder does not exist: " + folder.getPath());
        }
        if (!folder.isDirectory()) {
            throw new ValidationException("Path is not a directory: " + folder.getPath());
        }
        if (!folder.canRead()) {
            throw new ValidationException("Cannot read folder: " + folder.getPath());
        }
    }

    public static void validateString(String str) {
        if (str == null || str.trim().isEmpty()) {
            throw new AssertionError("String cannot be null or empty");
        }
    }
}
