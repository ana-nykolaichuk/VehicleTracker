package com.oracle.vehicle.validator;

import com.oracle.vehicle.exception.TrackingException;
import com.oracle.vehicle.exception.ValidationMessage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CoordinatesValidatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CoordinatesValidator unit = new CoordinatesValidator();

    @Test
    public void validateIncorrectLeftLongitude() {
        thrown.expect(TrackingException.class);

        String expectedMsg = String.format(ValidationMessage.COORDINATE_IS_INVALID.getMessage(), -190f);
        thrown.expectMessage(expectedMsg);

        unit.validate(-190f, -5f, 17.385f, 0.539f);
    }

    @Test
    public void validateIncorrectLeftLatitude() {
        thrown.expect(TrackingException.class);

        String expectedMsg = String.format(ValidationMessage.COORDINATE_IS_INVALID.getMessage(), -190f);
        thrown.expectMessage(expectedMsg);

        unit.validate(-19f, -190f, 17.385f, 0.539f);
    }

    @Test
    public void validateIncorrectRightLongitude() {
        thrown.expect(TrackingException.class);

        String expectedMsg = String.format(ValidationMessage.COORDINATE_IS_INVALID.getMessage(), -190f);
        thrown.expectMessage(expectedMsg);

        unit.validate(-19f, -5f, -190f, 0.539f);
    }

    @Test
    public void validateIncorrectRightLatitude() {
        thrown.expect(TrackingException.class);

        String expectedMsg = String.format(ValidationMessage.COORDINATE_IS_INVALID.getMessage(), -190f);
        thrown.expectMessage(expectedMsg);

        unit.validate(-19f, -5f, 17.385f, -190f);
    }

    @Test
    public void validateImpossibleRectangle() {
        thrown.expect(TrackingException.class);
        thrown.expectMessage(ValidationMessage.ZONE_IS_IMPOSSIBLE.getMessage());

        unit.validate(4f, -5f, 0.385f, -19f);
    }

    @Test
    public void validateSuccess() {
        unit.validate(4f, -5f, 12.385f, -19f);
    }

}