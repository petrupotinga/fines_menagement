package com.potinga.springboot.fines_menagement.repository;

import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<VehicleEntity> findByLicensePlate(String licensePlate);
    Optional<VehicleEntity> findByVin(String vin);
}
