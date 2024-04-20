package com.potinga.springboot.fines_menagement.repository;

import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {
}
