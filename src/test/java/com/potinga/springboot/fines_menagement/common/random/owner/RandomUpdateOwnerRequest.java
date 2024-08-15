package com.potinga.springboot.fines_menagement.common.random.owner;

import com.potinga.springboot.fines_menagement.dto.rest.owner.UpdateOwnerRequest;
import lombok.Builder;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.function.Supplier;

@Builder
public class RandomUpdateOwnerRequest implements Supplier<UpdateOwnerRequest> {
    @Builder.Default
    private final String idnp = RandomStringUtils.randomAlphabetic(13);
    @Builder.Default
    private final String firstName = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private final String lastName = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private final String birthDate = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private final String address = RandomStringUtils.randomAlphabetic(20);
    @Builder.Default
    private final String phoneNumber = RandomStringUtils.randomAlphabetic(20);

    @Override
    public UpdateOwnerRequest get() {
        return new UpdateOwnerRequest(idnp,firstName, lastName, birthDate, address, phoneNumber);
    }
}
