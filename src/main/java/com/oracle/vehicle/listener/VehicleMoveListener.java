package com.oracle.vehicle.listener;

import com.oracle.vehicle.model.domain.Vehicle;
import com.oracle.vehicle.model.mapper.VehicleMapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VehicleMoveListener {

    @Value("${vehicle.tracking.cache}")
    private String cacheName = "default";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private VehicleMapper vehicleMapper;

    @KafkaListener(topics = "vehicles", groupId = "vehicle-group")
    public void updateVehicle(@Payload Vehicle message) {
        log.info("Detected vehicle changes: [{}]", message);
        Element cachingElement = new Element(message.getVehicleName(), vehicleMapper.toVehicleDb(message));
        cacheManager.getCache(cacheName)
                    .putWithWriter(cachingElement);
    }

}
