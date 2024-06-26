package com.potinga.springboot.fines_menagement.dto.rest.owner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerCreatedResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public OwnerCreatedResponse() {
    }

    public OwnerCreatedResponse(Long id, String firstName, String lastName, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
