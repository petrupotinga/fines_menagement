package com.potinga.springboot.fines_menagement.common.random.vehicle;

import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import lombok.Builder;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.function.Supplier;

@Builder
public class RandomVehicle implements Supplier<VehicleEntity> {

    @Builder.Default
    private String vin = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private String licensePlate = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private String make = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private String model = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private int year = Integer.parseInt(RandomStringUtils.randomNumeric(4));

    @Override
    public VehicleEntity get() {
        return new VehicleEntity(vin, licensePlate, make, model, year);
    }
}
