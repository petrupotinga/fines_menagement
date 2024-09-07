package com.potinga.springboot.fines_menagement.dto.rest.vehicle;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@Getter
@Setter
public class CreateVehicleRequest {
    private String vin;
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private Long ownerId;

    public CreateVehicleRequest(String vin, String licensePlate, String make, String model, int year, Long ownerId) {
        this.vin = vin;
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.ownerId = ownerId;
    }
}
