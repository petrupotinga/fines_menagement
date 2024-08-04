package com.potinga.springboot.fines_menagement.dto.rest.owner;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerCreatedResponse {
    private Long id;
    private String idnp;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String address;
    private String phoneNumber;
}
