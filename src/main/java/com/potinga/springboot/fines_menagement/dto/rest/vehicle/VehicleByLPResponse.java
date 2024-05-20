package com.potinga.springboot.fines_menagement.dto.rest.vehicle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleByLPResponse {
    private Long id;
    private String vin;
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private long ownerId;

    public VehicleByLPResponse() {
    }

    public VehicleByLPResponse(Long id, String vin, String licensePlate, long ownerId, String make, String model, int year) {
        this.id = id;
        this.vin = vin;
        this.licensePlate = licensePlate;
        this.ownerId = ownerId;
        this.make = make;
        this.model = model;
        this.year = year;
    }
}
