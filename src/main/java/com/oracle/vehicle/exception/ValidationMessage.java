package com.oracle.vehicle.exception;

public enum ValidationMessage {

    COORDINATE_NOT_EXISTS("Given coordinate {} is not provided"),
    COORDINATE_IS_INVALID("Given coordinate {} is invalid"),
    ZONE_IS_IMPOSSIBLE("Described zone is impossible");

    private String message;

    ValidationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
