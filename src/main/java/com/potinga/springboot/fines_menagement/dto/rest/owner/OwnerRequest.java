package com.potinga.springboot.fines_menagement.dto.rest.owner;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OwnerRequest {
    private String firstName;
    private String lastName;
    private String birthDate;
}
