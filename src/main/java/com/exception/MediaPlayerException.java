package com.exception;

public class MediaPlayerException extends RuntimeException {
    public MediaPlayerException(String message) {
        super(message);
    }

    public MediaPlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}