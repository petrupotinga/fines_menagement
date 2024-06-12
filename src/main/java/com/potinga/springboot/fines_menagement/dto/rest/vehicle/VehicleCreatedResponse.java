package com.potinga.springboot.fines_menagement.dto.rest.vehicle;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@Getter
@Setter
public class VehicleCreatedResponse {
    private Long id;
    private String vin;
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private long ownerId;

    public VehicleCreatedResponse(Long id, String vin, String licensePlate, String make, String model, int year, long ownerId) {
        this.id = id;
        this.vin = vin;
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.ownerId = ownerId;
    }
}
