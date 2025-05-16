package com.repository.util;

import com.repository.exception.ValidationException;

import java.io.File;

public class InputValidator {
    private InputValidator() {
        // Private constructor to prevent instantiation
    }

    public static void validateTrack(String name, String artist, String album, int duration, String path) {
        validateStringName(name);
        validateStringName(artist);
        validateStringName(album);
        validateDuration(duration);
        validatePath(path);
    }

    public static void validateStringName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Track name cannot be null or empty");
        }
        if (name.length() > 255) {  // Example of a reasonable limit
            throw new ValidationException("Track name exceeds maximum length (255 characters)");
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
