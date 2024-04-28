package com.potinga.springboot.fines_menagement.repository;

import com.potinga.springboot.fines_menagement.entity.FineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<FineEntity, Long> {
}
