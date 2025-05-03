package com.exception;

public class MediaPlaybackException extends MediaPlayerException {
    public MediaPlaybackException(String message) {
        super(message);
    }

    public MediaPlaybackException(String message, Throwable cause) {
        super(message, cause);
    }
}
