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
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vin;
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<FineEntity> fines = new ArrayList<>();

    public VehicleEntity(String vin, String licensePlate, String make, String model, int year) {
        this.vin = vin;
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.year = year;
    }
}


