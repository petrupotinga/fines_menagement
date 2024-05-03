package com.potinga.springboot.fines_menagement.dto.rest.owner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerByIdResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public OwnerByIdResponse(Long id, String firstName, String lastName, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
