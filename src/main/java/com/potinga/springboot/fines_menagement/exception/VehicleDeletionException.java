package com.potinga.springboot.fines_menagement.exception;

public class VehicleDeletionException extends EntityDeletionException {
    public VehicleDeletionException(String message) {
        super(message);
    }

    VehicleDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
