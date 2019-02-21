package com.oracle.vehicle.validator.rule;

import com.oracle.vehicle.exception.TrackingException;
import com.oracle.vehicle.exception.ValidationException;

public interface AbstractRule {

    ValidationException apply();

}
