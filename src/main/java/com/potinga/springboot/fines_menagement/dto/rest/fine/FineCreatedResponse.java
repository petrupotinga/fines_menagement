package com.potinga.springboot.fines_menagement.dto.rest.fine;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FineCreatedResponse {
    private Long fineId;
    private Double amount;
    private String violation;
    private String date;
    private String location;
    private Long ownerId;
    private Long vehicleId;

    public FineCreatedResponse(Long fineId, Double amount, String violation, String date, String location, Long ownerId, Long vehicleId) {
        this.fineId = fineId;
        this.amount = amount;
        this.violation = violation;
        this.date = date;
        this.location = location;
        this.ownerId = ownerId;
        this.vehicleId = vehicleId;
    }
}
