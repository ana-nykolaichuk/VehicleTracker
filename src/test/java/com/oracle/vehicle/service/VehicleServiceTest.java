package com.oracle.vehicle.service;

import com.oracle.vehicle.model.domain.Vehicle;
import com.oracle.vehicle.model.mapper.VehicleMapper;
import com.oracle.vehicle.model.persistence.VehicleDb;
import com.oracle.vehicle.repository.VehicleRepository;
import com.oracle.vehicle.validator.CoordinatesValidator;
import com.oracle.vehicle.validator.VehicleValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {

    @Mock
    private CoordinatesValidator coordinatesValidator;

    @Mock
    private VehicleValidator vehicleValidator;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @Captor
    private ArgumentCaptor<Message> messageArgumentCaptor;

    @InjectMocks
    private VehicleService unit;

    @Test
    public void getVehiclesWithinRectangle() {
        float leftLon = -5f;
        float leftLat = -5f;
        float rightLon = 5f;
        float rightLat = 5f;

        VehicleDb vehicleDb = new VehicleDb("VehicleName", 3.5f, 0.1234f);
        when(vehicleRepository.getVehiclesInArea(leftLon, leftLat, rightLon, rightLat))
                .thenReturn(Collections.singletonList(vehicleDb));

        Vehicle vehicle = new Vehicle("VehicleName", 3.5f, 0.1234f);
        when(vehicleMapper.toVehicleList(Collections.singletonList(vehicleDb)))
                .thenReturn(Collections.singletonList(vehicle));

        List<Vehicle> actual = unit.getVehiclesWithinRectangle(leftLon, leftLat, rightLon, rightLat);

        assertEquals(Collections.singletonList(vehicle), actual);

        verify(coordinatesValidator).validate(leftLon, leftLat, rightLon, rightLat);
    }

    @Test
    public void updateVehicle() {
        String vehicleName = "vehicleName";
        float lon = -5.8423f;
        float lat = 0.5732f;
        unit.updateVehicle(vehicleName, lon, lat);

        verify(vehicleValidator).validate(lon, lat);

        Message<Vehicle> expectedMessage = MessageBuilder.withPayload(new Vehicle(vehicleName, lon, lat))
                                                         .setHeader(KafkaHeaders.MESSAGE_KEY, vehicleName)
                                                         .setHeader(KafkaHeaders.TOPIC, "vehicles")
                                                         .build();
        verify(kafkaTemplate).send(messageArgumentCaptor.capture());

        Message<Vehicle> actual = messageArgumentCaptor.getValue();

        assertEquals(expectedMessage.getPayload(), actual.getPayload());
        assertEquals(expectedMessage.getHeaders().get(KafkaHeaders.MESSAGE_KEY),
                actual.getHeaders().get(KafkaHeaders.MESSAGE_KEY));
        assertEquals(expectedMessage.getHeaders().get(KafkaHeaders.TOPIC),
                actual.getHeaders().get(KafkaHeaders.TOPIC));
    }


}