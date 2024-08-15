package com.potinga.springboot.fines_menagement.repository.vehicle;

import com.potinga.springboot.fines_menagement.common.PostgresDataJpaTest;
import com.potinga.springboot.fines_menagement.entity.FineEntity;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import com.potinga.springboot.fines_menagement.repository.FineRepository;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.repository.VehicleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@PostgresDataJpaTest
class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private FineRepository fineRepository;

    @Test
    @DisplayName("Test vehicle creation fails with duplicate VIN")
    void testUniqueVinConstraint() {
        //  Given
        OwnerEntity ownerEntityTransient = new OwnerEntity();
        ownerEntityTransient.setIdnp(RandomStringUtils.randomAlphabetic(13));
        ownerEntityTransient.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setBirthDate(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        String vin = RandomStringUtils.randomAlphabetic(10);

        VehicleEntity vehicleEntityTransient1 = new VehicleEntity();
        vehicleEntityTransient1.setMake(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient1.setModel(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient1.setVin(vin);
        vehicleEntityTransient1.setYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicleEntityTransient1.setLicensePlate(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient1.setOwner(persistedOwnerEntity);

        VehicleEntity vehicleEntityTransient2 = new VehicleEntity();
        vehicleEntityTransient2.setMake(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient2.setModel(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient2.setVin(vin);
        vehicleEntityTransient2.setYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicleEntityTransient2.setLicensePlate(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient2.setOwner(persistedOwnerEntity);

        // WHEN
        vehicleRepository.save(vehicleEntityTransient1);

        // THEN
        assertThrows(DataIntegrityViolationException.class, () -> {
            vehicleRepository.saveAndFlush(vehicleEntityTransient2);
        });
    }

    @Test
    @DisplayName("Test vehicle creation fails with duplicate Licence Plate")
    void testUniqueLicencePlateConstraint() {
        //  Given
        OwnerEntity ownerEntityTransient = new OwnerEntity();
        ownerEntityTransient.setIdnp(RandomStringUtils.randomAlphabetic(13));
        ownerEntityTransient.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setBirthDate(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        String licencePlate = RandomStringUtils.randomAlphabetic(10);

        VehicleEntity vehicleEntityTransient1 = new VehicleEntity();
        vehicleEntityTransient1.setMake(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient1.setModel(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient1.setVin(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient1.setYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicleEntityTransient1.setLicensePlate(licencePlate);
        vehicleEntityTransient1.setOwner(persistedOwnerEntity);

        VehicleEntity vehicleEntityTransient2 = new VehicleEntity();
        vehicleEntityTransient2.setMake(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient2.setModel(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient2.setVin(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient2.setYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicleEntityTransient2.setLicensePlate(licencePlate);
        vehicleEntityTransient2.setOwner(persistedOwnerEntity);

        // WHEN
        vehicleRepository.save(vehicleEntityTransient1);

        // THEN
        assertThrows(DataIntegrityViolationException.class, () -> {
            vehicleRepository.saveAndFlush(vehicleEntityTransient2);
        });
    }

    @Test
    @DisplayName("Delete vehicle test")
    void testDeleteVehicle() {
        //  Given
        OwnerEntity ownerEntityTransient = new OwnerEntity();
        ownerEntityTransient.setIdnp(RandomStringUtils.randomAlphabetic(13));
        ownerEntityTransient.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setBirthDate(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        String licencePlate = RandomStringUtils.randomAlphabetic(10);

        VehicleEntity vehicleEntityTransient1 = new VehicleEntity();
        vehicleEntityTransient1.setMake(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient1.setModel(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient1.setVin(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient1.setYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicleEntityTransient1.setLicensePlate(licencePlate);
        vehicleEntityTransient1.setOwner(persistedOwnerEntity);

        VehicleEntity vehiclePersisted = vehicleRepository.save(vehicleEntityTransient1);

        FineEntity fineTransient = new FineEntity();
        fineTransient.setAmount(100D);
        fineTransient.setViolation("Violation");
        fineTransient.setDate("3 Decembrie 2023");
        fineTransient.setLocation("Alba Iulia");
        fineTransient.setOwner(persistedOwnerEntity);
        fineTransient.setVehicle(vehiclePersisted);
        vehiclePersisted.getFines().add(fineTransient);

        FineEntity persistedFine = fineRepository.save(fineTransient);

        // THEN
        vehicleRepository.deleteById(vehiclePersisted.getId());

        // Assert the vehicle is deleted
        Optional<VehicleEntity> deletedVehicle = vehicleRepository.findById(vehiclePersisted.getId());
        assertThat(deletedVehicle).isEmpty();

        // Assert the owner is not deleted
        Optional<OwnerEntity> ownerById = ownerRepository.findById(persistedOwnerEntity.getId());
        assertThat(ownerById)
                .as("The owner need to be present on delete a vehicle but it was deleted")
                .isPresent();

        // Assert the owner is not deleted
        Optional<FineEntity> finesById = fineRepository.findById(persistedFine.getId());
        assertThat(finesById)
                .as("The fine need to be present on delete a vehicle but it was deleted")
                .isPresent();
    }
}