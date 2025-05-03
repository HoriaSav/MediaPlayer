package com.exception;

public class TrackNotFoundException extends MediaPlayerException {
    public TrackNotFoundException(String message) {
        super(message);
    }
}
