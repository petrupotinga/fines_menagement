package com.potinga.springboot.fines_menagement.repository;

import com.potinga.springboot.fines_menagement.entity.FineEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FineRepository extends JpaRepository<FineEntity, Long> {

    List<FineEntity> findByVehicleId(Long id);
    @Query("SELECT f FROM FineEntity f WHERE f.vehicle.licensePlate = :licensePlate")
    List<FineEntity> findAllByVehicleLicensePlate(@Param("licensePlate") String licensePlate);
}
