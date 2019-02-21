package com.oracle.vehicle.endpoint;

import com.oracle.vehicle.model.api.VehicleApi;
import com.oracle.vehicle.model.domain.Vehicle;
import com.oracle.vehicle.model.mapper.VehicleMapper;
import com.oracle.vehicle.service.VehicleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VehicleEndpointTest {

    private static final String VEHICLE_NAME = "VehicleName";

    @Mock
    private VehicleMapper vehicleMapper;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleEndpoint unit;

    @Test
    public void getVehiclesWithinRectangle() {
        Vehicle vehicle = new Vehicle(VEHICLE_NAME, -2.4353f, 16.3758f);
        VehicleApi vehicleApi = new VehicleApi(VEHICLE_NAME, -2.4353f, 16.3758f);
        when(vehicleMapper.toVehicleApiList(Collections.singletonList(vehicle)))
                .thenReturn(Collections.singletonList(vehicleApi));
        when(vehicleService.getVehiclesWithinRectangle(-5.0f, -5.0f, 17f, 17f))
                .thenReturn(Collections.singletonList(vehicle));

        List<VehicleApi> actual = unit.getVehiclesWithinRectangle(-5f, -5f, 17f, 17f);

        assertEquals(Collections.singletonList(vehicleApi), actual);
    }

    @Test
    public void trackVehicle() {
        VehicleApi vehicleApi = new VehicleApi(VEHICLE_NAME, -2.4353f, 16.3758f);

        unit.trackVehicle(VEHICLE_NAME, vehicleApi);

        verify(vehicleService).updateVehicle(VEHICLE_NAME, vehicleApi.getLongitude(), vehicleApi.getLatitude());
    }

}