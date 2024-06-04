package com.potinga.springboot.fines_menagement.unit.vehicle;

import com.potinga.springboot.fines_menagement.dto.rest.vehicle.CreateVehicleRequest;
import com.potinga.springboot.fines_menagement.entity.FineEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import com.potinga.springboot.fines_menagement.exception.DuplicateRecordException;
import com.potinga.springboot.fines_menagement.exception.VehicleDeletionException;
import com.potinga.springboot.fines_menagement.repository.FineRepository;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.repository.VehicleRepository;
import com.potinga.springboot.fines_menagement.service.VehicleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private FineRepository fineRepository;
    @InjectMocks
    private VehicleService vehicleService;

    @Test
    @DisplayName("Test vehicle creation fails with duplicate VIN")
    void testCreateVehicleWithDuplicateVin() {
        CreateVehicleRequest request = new CreateVehicleRequest();
        request.setVin("VIN123456");
        request.setMake("Toyota");
        request.setModel("Camry");
        request.setYear(2020);
        request.setLicensePlate("ABC123");
        request.setOwnerId(1L);

        VehicleEntity existingVehicle = new VehicleEntity();
        existingVehicle.setVin(request.getVin());

        when(vehicleRepository.findByVin(request.getVin())).thenReturn(Optional.of(existingVehicle));

        DuplicateRecordException thrown = assertThrows(DuplicateRecordException.class, () -> {
            vehicleService.createVehicle(request);
        });

        assertEquals("Could not create vehicle with same vin number: %s".formatted(request.getVin()), thrown.getMessage());
        verify(vehicleRepository, times(1)).findByVin(request.getVin());
        verify(vehicleRepository, never()).save(any(VehicleEntity.class));
    }

    @Test
    @DisplayName("Test vehicle creation fails with duplicate Licence Plate")
    void testCreateVehicleWithDuplicateLicencePlate() {
        CreateVehicleRequest request = new CreateVehicleRequest();
        request.setVin("VIN123456");
        request.setMake("Toyota");
        request.setModel("Camry");
        request.setYear(2020);
        request.setLicensePlate("ABC123");
        request.setOwnerId(1L);

        VehicleEntity existingVehicle = new VehicleEntity();
        existingVehicle.setLicensePlate(request.getLicensePlate());

        when(vehicleRepository.findByLicensePlate(request.getLicensePlate())).thenReturn(Optional.of(existingVehicle));

        DuplicateRecordException thrown = assertThrows(DuplicateRecordException.class, () -> {
            vehicleService.createVehicle(request);
        });

        assertEquals("Could not create vehicle with same licence plate: %s".formatted(request.getLicensePlate()), thrown.getMessage());
        verify(vehicleRepository, times(1)).findByLicensePlate(request.getLicensePlate());
        verify(vehicleRepository, never()).save(any(VehicleEntity.class));
    }

    @Test
    @DisplayName("Test when a vehicle has fines, it cannot be deleted")
    void testDeleteVehicleHavingFines() {
        long vehicleId = 1L;
        // Simulate a vehicle with fines
        when(fineRepository.findByVehicleId(vehicleId)).thenReturn(List.of(new FineEntity())); // simulate 1 fine

        // Verify that the vehicle is not deleted
        VehicleDeletionException thrown = assertThrows(VehicleDeletionException.class, () -> {
            vehicleService.deleteVehicle(vehicleId);
        });

        assertEquals("Cannot delete vehicle with id = %s having fines".formatted(vehicleId), thrown.getMessage());
        verify(fineRepository, times(1)).findByVehicleId(vehicleId);
        verify(vehicleRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Test when a vehicle has 0 fines, it can be deleted successfully")
    void testDeleteVehicleNotHavingFines() {
        long vehicleId = 1L;
        // Simulate a vehicle without fines
        when(fineRepository.findByVehicleId(vehicleId)).thenReturn(List.of()); // simulate 0 fines

        // Try to delete the vehicle
        vehicleService.deleteVehicle(vehicleId);

        // Verify that the vehicle delete method was called
        verify(fineRepository, times(1)).findByVehicleId(vehicleId);
        verify(vehicleRepository, times(1)).deleteById(vehicleId);
    }
}