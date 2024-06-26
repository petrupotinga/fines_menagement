package com.potinga.springboot.fines_menagement.dto.rest.owner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllOwnerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    public AllOwnerResponse(String firstName, String lastName, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public AllOwnerResponse(Long id, String firstName, String lastName, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

}
