package com.oracle.vehicle.validator.rule.coordinate;

import com.oracle.vehicle.exception.ValidationException;
import com.oracle.vehicle.exception.ValidationMessage;
import com.oracle.vehicle.validator.rule.AbstractRule;

public class LongitudeCorrectnessRule implements AbstractRule {

    private float longitude;

    public LongitudeCorrectnessRule(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public ValidationException apply() {
        return isInvalidLongitude() ? new ValidationException(ValidationMessage.COORDINATE_IS_INVALID, longitude)
                                    : null;
    }

    private boolean isInvalidLongitude() {
        return longitude <= -180 || longitude >= 180;
    }

}
