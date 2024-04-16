package com.potinga.springboot.fines_menagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "fine")
public class FineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineid;
    private double amount;
    private String violation;
    private String date;
    private String location;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    public FineEntity(double amount, String violation, String date, String location) {
        this.amount = amount;
        this.violation = violation;
        this.date = date;
        this.location = location;
    }
}
