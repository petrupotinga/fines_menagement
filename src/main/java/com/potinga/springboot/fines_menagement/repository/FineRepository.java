package com.potinga.springboot.fines_menagement.repository;

import com.potinga.springboot.fines_menagement.entity.FineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineRepository extends JpaRepository<FineEntity, Long> {

    List<FineEntity> findByVehicleId(Long id);
}
