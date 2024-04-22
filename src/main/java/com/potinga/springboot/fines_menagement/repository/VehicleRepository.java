package com.potinga.springboot.fines_menagement.repository;

import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
}
