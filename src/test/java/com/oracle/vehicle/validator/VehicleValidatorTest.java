package com.oracle.vehicle.validator;

import com.oracle.vehicle.exception.TrackingException;
import com.oracle.vehicle.exception.ValidationMessage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class VehicleValidatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private VehicleValidator unit = new VehicleValidator();

    @Test
    public void validateIncorrectLongitude() {
        thrown.expect(TrackingException.class);

        String expectedMsg = String.format(ValidationMessage.COORDINATE_IS_INVALID.getMessage(), -190f);
        thrown.expectMessage(expectedMsg);

        unit.validate(-190f, -5f);
    }

    @Test
    public void validateIncorrectLatitude() {
        thrown.expect(TrackingException.class);

        String expectedMsg = String.format(ValidationMessage.COORDINATE_IS_INVALID.getMessage(), -190f);
        thrown.expectMessage(expectedMsg);

        unit.validate(-19f, -190f);
    }

    @Test
    public void validateSuccess() {
        unit.validate(4f, -5f);
    }

}