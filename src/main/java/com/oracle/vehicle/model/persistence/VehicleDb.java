package com.oracle.vehicle.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "vehicle")
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDb {

    @Id
    @Column(name = "vehicle_name")
    private String vehicleName;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "latitude")
    private float latitude;

}
