package com.oracle.vehicle.service;

import com.oracle.vehicle.model.domain.Vehicle;
import com.oracle.vehicle.model.mapper.VehicleMapper;
import com.oracle.vehicle.model.persistence.VehicleDb;
import com.oracle.vehicle.repository.VehicleRepository;
import com.oracle.vehicle.validator.CoordinatesValidator;
import com.oracle.vehicle.validator.VehicleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class VehicleService {

    @Value("${kafka.vehicles.topic}")
    private String topicName = "vehicles";

    @Autowired
    private CoordinatesValidator coordinatesValidator;

    @Autowired
    private VehicleValidator vehicleValidator;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleMapper vehicleMapper;

    public List<Vehicle> getVehiclesWithinRectangle(float leftLon, float leftLat,
                                                    float rightLon, float rightLat) {
        log.info("Validating coordinates: left lon {}, left lat {}, right lon {}, right lat {}",
                leftLon, leftLat, rightLon, rightLat);
        coordinatesValidator.validate(leftLon, leftLat, rightLon, rightLat);

        log.info("Getting vehicles in area");
        List<VehicleDb> vehiclesInArea = vehicleRepository.getVehiclesInArea(leftLon, leftLat, rightLon, rightLat);
        return vehicleMapper.toVehicleList(vehiclesInArea);
    }

    public void updateVehicle(String vehicleName, float lon, float lat) {
        log.info("Validates vehicle: name {}, longitude {}, latitude {}", vehicleName, lon, lat);
        vehicleValidator.validate(lon, lat);

        log.info("Sending message to update vehicle");
        Message<Vehicle> msg = MessageBuilder.withPayload(new Vehicle(vehicleName, lon, lat))
                                             .setHeader(KafkaHeaders.MESSAGE_KEY, vehicleName)
                                             .setHeader(KafkaHeaders.TOPIC, topicName)
                                             .build();
        kafkaTemplate.send(msg);
    }

}
