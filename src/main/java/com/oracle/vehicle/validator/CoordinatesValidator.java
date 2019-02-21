package com.oracle.vehicle.validator;

import com.oracle.vehicle.validator.rule.coordinate.LatitudeCorrectnessRule;
import com.oracle.vehicle.validator.rule.coordinate.LongitudeCorrectnessRule;
import com.oracle.vehicle.validator.rule.coordinate.RectanglePossibleRule;
import org.springframework.stereotype.Component;

@Component
public class CoordinatesValidator extends AbstractValidator {

    public void validate(float leftLon, float leftLat,
                         float rightLon, float rightLat) {
        this.useRule(new LongitudeCorrectnessRule(leftLon))
            .useRule(new LatitudeCorrectnessRule(leftLat))
            .useRule(new LongitudeCorrectnessRule(rightLon))
            .useRule(new LatitudeCorrectnessRule(rightLat))
            .useRule(new RectanglePossibleRule(leftLon, leftLat, rightLon, rightLat))
            .apply();
    }

}
