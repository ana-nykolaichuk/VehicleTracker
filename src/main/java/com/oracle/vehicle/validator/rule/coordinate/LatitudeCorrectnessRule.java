package com.oracle.vehicle.validator.rule.coordinate;

import com.oracle.vehicle.exception.ValidationException;
import com.oracle.vehicle.exception.ValidationMessage;
import com.oracle.vehicle.validator.rule.AbstractRule;

public class LatitudeCorrectnessRule implements AbstractRule {

    private float latitude;

    public LatitudeCorrectnessRule(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public ValidationException apply() {
        return isInvalidLatitude() ? new ValidationException(ValidationMessage.COORDINATE_IS_INVALID, latitude)
                                   : null;
    }

    private boolean isInvalidLatitude() {
        return latitude <= -90 || latitude >= 90;
    }
}
