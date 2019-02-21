package com.oracle.vehicle.validator.rule.coordinate;

import com.oracle.vehicle.exception.ValidationException;
import com.oracle.vehicle.exception.ValidationMessage;
import com.oracle.vehicle.validator.rule.AbstractRule;

public class RectanglePossibleRule implements AbstractRule {

    private float leftLon;
    private float leftLat;
    private float rightLon;
    private float rightLat;

    public RectanglePossibleRule(float leftLon, float leftLat, float rightLon, float rightLat) {
        this.leftLon = leftLon;
        this.leftLat = leftLat;
        this.rightLon = rightLon;
        this.rightLat = rightLat;
    }

    @Override
    public ValidationException apply() {
        return inRectangleImpossible() ? new ValidationException(ValidationMessage.ZONE_IS_IMPOSSIBLE)
                                       : null;
    }

    private boolean inRectangleImpossible() {
        return leftLon > rightLon && leftLat > rightLat;
    }
}
