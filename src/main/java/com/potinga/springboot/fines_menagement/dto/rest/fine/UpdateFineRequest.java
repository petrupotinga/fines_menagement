package com.potinga.springboot.fines_menagement.dto.rest.fine;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateFineRequest {
    private double amount;
    private String violation;
    private String date;
    private String location;
}
