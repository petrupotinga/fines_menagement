package com.potinga.springboot.fines_menagement.repository;

import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {
    Optional<OwnerEntity> findByIdnp(String idnp);

    Optional<OwnerEntity> findByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, String birthDate);
}
