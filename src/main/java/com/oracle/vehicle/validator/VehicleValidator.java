package com.oracle.vehicle.validator;


import com.oracle.vehicle.validator.rule.coordinate.LatitudeCorrectnessRule;
import com.oracle.vehicle.validator.rule.coordinate.LongitudeCorrectnessRule;
import org.springframework.stereotype.Component;

@Component
public class VehicleValidator extends AbstractValidator {

    public void validate(float longitude, float latitude) {
        this.useRule(new LatitudeCorrectnessRule(latitude))
            .useRule(new LongitudeCorrectnessRule(longitude))
            .apply();
    }

}
