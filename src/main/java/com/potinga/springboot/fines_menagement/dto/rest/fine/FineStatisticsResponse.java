package com.potinga.springboot.fines_menagement.dto.rest.fine;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FineStatisticsResponse {
    private long totalFines;
    private double totalAmount;
    private double averageAmount;
}
