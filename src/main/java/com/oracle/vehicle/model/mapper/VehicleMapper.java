package com.oracle.vehicle.model.mapper;

import com.oracle.vehicle.model.api.VehicleApi;
import com.oracle.vehicle.model.domain.Vehicle;
import com.oracle.vehicle.model.persistence.VehicleDb;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface VehicleMapper {

    VehicleApi toVehicleApi(Vehicle vehicle);

    List<VehicleApi> toVehicleApiList(List<Vehicle> vehicle);

    VehicleDb toVehicleDb(Vehicle vehicle);

    Vehicle toVehicle(VehicleDb vehicleDb);

    List<Vehicle> toVehicleList(List<VehicleDb> vehicleDbs);
}
