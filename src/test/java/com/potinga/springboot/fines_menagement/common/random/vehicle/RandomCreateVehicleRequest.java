package com.potinga.springboot.fines_menagement.common.random.vehicle;

import com.potinga.springboot.fines_menagement.dto.rest.vehicle.CreateVehicleRequest;
import lombok.Builder;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.function.Supplier;

@Builder
public class RandomCreateVehicleRequest implements Supplier<CreateVehicleRequest> {
    @Builder.Default
    private String vin = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private String licensePlate = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private Long ownerId = null;
    @Builder.Default
    private String make = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private String model = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private int year = Integer.parseInt(RandomStringUtils.randomNumeric(4));

    @Override
    public CreateVehicleRequest get() {
        return new CreateVehicleRequest(vin, licensePlate, ownerId, make, model, year);
    }
}
