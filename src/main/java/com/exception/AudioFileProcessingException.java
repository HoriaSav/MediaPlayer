package com.exception;

public class AudioFileProcessingException extends MediaPlayerException {
    public AudioFileProcessingException(String message) {
        super(message);
    }

    public AudioFileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}