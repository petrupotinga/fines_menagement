package com.potinga.springboot.fines_menagement.exception;

public abstract class EntityDeletionException extends RuntimeException {
    EntityDeletionException(String message) {
        super(message);
    }

    EntityDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
