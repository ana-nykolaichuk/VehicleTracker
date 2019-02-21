package com.oracle.vehicle.exception;

import java.util.Objects;

public class ValidationException {

    private ValidationMessage message;
    private Object[] params;

    public ValidationException(ValidationMessage message, Object... params) {
        this.message = message;
        this.params = params;
    }

    public String getValidationMessage() {
        return String.format(message.getMessage(), params);
    }
}
