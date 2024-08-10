package com.potinga.springboot.fines_menagement.dto.rest.owner;

import lombok.*;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOwnerRequest {
    private String idnp;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String address;
    private String phoneNumber;
}