package com.potinga.springboot.fines_menagement.dto.rest.owner;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class AllOwnerResponse {
    private Long id;
    private String idnp;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String address;
    private String phoneNumber;
    public AllOwnerResponse(String idnp, String firstName, String lastName, String birthDate, String address, String phoneNumber) {
        this.idnp = idnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public AllOwnerResponse(Long id, String idnp, String firstName, String lastName,String birthDate, String address, String phoneNumber) {
        this.id = id;
        this.idnp = idnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
