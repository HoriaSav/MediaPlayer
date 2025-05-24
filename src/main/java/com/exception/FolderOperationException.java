package com.exception;

public class FolderOperationException extends MediaPlayerException {
    public FolderOperationException(String message) {
        super(message);
    }

    public FolderOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}

