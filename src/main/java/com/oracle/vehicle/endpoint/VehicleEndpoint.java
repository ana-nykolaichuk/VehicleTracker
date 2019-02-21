package com.oracle.vehicle.endpoint;

import com.oracle.vehicle.model.api.VehicleApi;
import com.oracle.vehicle.model.mapper.VehicleMapper;
import com.oracle.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/vehicle")
public class VehicleEndpoint {

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public List<VehicleApi> getVehiclesWithinRectangle(@RequestParam("left_lon") float leftLon,
                                                       @RequestParam("left_lat") float leftLat,
                                                       @RequestParam("right_lon") float rightLon,
                                                       @RequestParam("right_lat") float rightLat) {
        return vehicleMapper.toVehicleApiList(
                vehicleService.getVehiclesWithinRectangle(leftLon, leftLat, rightLon, rightLat));
    }

    @PutMapping("/track/{vehicleName}")
    public void trackVehicle(@PathVariable("vehicleName") String vehicleName,
                             @RequestBody VehicleApi vehicle) {
        vehicleService.updateVehicle(vehicleName, vehicle.getLongitude(), vehicle.getLatitude());
    }

}
