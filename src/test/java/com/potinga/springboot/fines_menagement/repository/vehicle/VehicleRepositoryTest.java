package com.potinga.springboot.fines_menagement.repository.vehicle;

import com.potinga.springboot.fines_menagement.common.PostgresDataJpaTest;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;

@PostgresDataJpaTest
class VehicleRepositoryTest {

    @Autowired
    private com.potinga.springboot.fines_menagement.repository.VehicleRepository vehicleRepository;
    @Autowired
    private com.potinga.springboot.fines_menagement.repository.OwnerRepository ownerRepository;

    @Test
    @DisplayName("Test vehicle creation fails with duplicate VIN")
    public void testUniqueVinConstraint() {
        //        GIVEN
        OwnerEntity ownerEntityTransient = new OwnerEntity();
        ownerEntityTransient.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setLastName(RandomStringUtils.randomAlphabetic(10));
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
    public void testUniqueLicencePlateConstraint() {
        //        GIVEN
        OwnerEntity ownerEntityTransient = new OwnerEntity();
        ownerEntityTransient.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setLastName(RandomStringUtils.randomAlphabetic(10));
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
}