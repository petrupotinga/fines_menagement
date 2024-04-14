package com.potinga.springboot.fines_menagement.apiclient;

import com.potinga.springboot.fines_menagement.common.BaseRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleApiClient {

    private static final String SAVE_VEHICLE = "http://localhost:{PORT}/api/v1/vehicles";
    private static final String GET_ALL_VEHICLES = "http://localhost:{PORT}/api/v1/vehicles";
    private static final String GET_VEHICLE_BY_ID = "http://localhost:{PORT}/api/v1/vehicles/{ID}";
    private static final String UPDATE_VEHICLE_BY_ID = "http://localhost:{PORT}/api/v1/vehicles/{ID}";
    private static final String DELETE_VEHICLE_BY_ID = "http://localhost:{PORT}/api/v1/vehicles/{ID}";

    @Autowired
    private BaseRestTemplate baseRestTemplate;
}
