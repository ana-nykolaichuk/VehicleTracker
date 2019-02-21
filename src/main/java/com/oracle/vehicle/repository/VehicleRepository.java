package com.oracle.vehicle.repository;

import com.oracle.vehicle.model.persistence.VehicleDb;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleDb, String> {

    @Query("SELECT v FROM VehicleDb v WHERE v.latitude BETWEEN :leftLat AND :rightLat " +
            "AND v.longitude BETWEEN :leftLon AND :rightLon")
    List<VehicleDb> getVehiclesInArea(@Param("leftLon") float leftLon,
                                      @Param("leftLat") float leftLat,
                                      @Param("rightLon") float rightLon,
                                      @Param("rightLat") float rightLat);
}


