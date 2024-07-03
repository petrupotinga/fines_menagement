package com.potinga.springboot.fines_menagement.common.random.fine;

import com.potinga.springboot.fines_menagement.entity.FineEntity;
import lombok.Builder;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
import java.util.function.Supplier;

@Builder
public class RandomFine implements Supplier<FineEntity> {
    private static final Random RANDOM = new Random();

    @Builder.Default
    private double amount = RANDOM.nextDouble();
    @Builder.Default
    private String violation = RandomStringUtils.randomAlphabetic(10);
    @Builder.Default
    private String date = RandomStringUtils.randomAlphabetic(10);
    @Builder.Default
    private String location = RandomStringUtils.randomAlphabetic(10);

    @Override
    public FineEntity get() {
        return new FineEntity(amount, violation, date, location);
    }
}
