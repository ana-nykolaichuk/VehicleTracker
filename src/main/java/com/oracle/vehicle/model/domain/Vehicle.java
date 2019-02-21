package com.oracle.vehicle.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    private String vehicleName;
    private float longitude;
    private float latitude;
}
