package com.potinga.springboot.fines_menagement.dto.rest.owner;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor

public class OwnerByIdnpResponse {
    private Long id;
    private String idnp;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public OwnerByIdnpResponse(Long id, String idnp, String firstName, String lastName, String address, String phoneNumber) {
        this.id = id;
        this.idnp = idnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
