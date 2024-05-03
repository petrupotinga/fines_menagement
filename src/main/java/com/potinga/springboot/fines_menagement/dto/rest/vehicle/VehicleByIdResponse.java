package com.potinga.springboot.fines_menagement.dto.rest.vehicle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleByIdResponse {
    private Long id;
    private String vin;
    private String licensePlate;
    private Long ownerId;
    private String make;
    private String model;
    private int year;

    public VehicleByIdResponse() {
    }

    public VehicleByIdResponse(Long id, String vin, String licensePlate, Long ownerId, String make, String model, int year) {
        this.id = id;
        this.vin = vin;
        this.licensePlate = licensePlate;
        this.ownerId = ownerId;
        this.make = make;
        this.model = model;
        this.year = year;
    }
}
