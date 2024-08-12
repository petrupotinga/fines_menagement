package com.potinga.springboot.fines_menagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "owner")
public class OwnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idnp;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String address;
    private String phoneNumber;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<VehicleEntity> vehicles = new ArrayList<>();
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<FineEntity> fines = new ArrayList<>();


    public OwnerEntity(String idnp, String firstName, String lastName, String birthDate, String address, String phoneNumber) {
        this.idnp = idnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
