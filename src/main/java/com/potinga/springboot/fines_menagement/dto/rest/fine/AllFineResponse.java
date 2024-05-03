package com.potinga.springboot.fines_menagement.dto.rest.fine;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AllFineResponse {
    private Long id;
    private double amount;
    private String violation;
    private String date;
    private String location;
    private Long ownerId;
    private Long vehicleId;

    public AllFineResponse(Long id, double amount, String violation, String date, String location, Long ownerId, Long vehicleId) {
        this.id = id;
        this.amount = amount;
        this.violation = violation;
        this.date = date;
        this.location = location;
        this.ownerId = ownerId;
        this.vehicleId = vehicleId;
    }
}
