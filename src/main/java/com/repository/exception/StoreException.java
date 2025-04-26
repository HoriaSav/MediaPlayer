package com.repository.exception;

public class StoreException extends ServiceException {
    private static final long serialVersionUID = 278863170038613468L;

    public StoreException() {
    }

    public StoreException(String message) {
        super(message);
    }

    public StoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreException(Throwable cause) {
        super(cause);
    }
}
