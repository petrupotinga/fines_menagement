package com.potinga.springboot.fines_menagement.dto.rest.vehicle;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateVehicleResponse {
    private String vin;
    private String licensePlate;
    private String make;
    private String model;
    private int year;
}