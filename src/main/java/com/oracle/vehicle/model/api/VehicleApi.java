package com.oracle.vehicle.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleApi {

    private String vehicleName;
    private float latitude;
    private float longitude;
}
