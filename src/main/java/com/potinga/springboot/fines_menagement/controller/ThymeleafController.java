package com.potinga.springboot.fines_menagement.controller;

import com.potinga.springboot.fines_menagement.dto.rest.owner.AllOwnerResponse;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.AllVehicleResponse;
import com.potinga.springboot.fines_menagement.service.OwnerService;
import com.potinga.springboot.fines_menagement.service.VehicleService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SpringBootApplication
@Controller
public class ThymeleafController {
    private final OwnerService ownerService;
    private final VehicleService vehicleService;

    public ThymeleafController(OwnerService ownerService, VehicleService vehicleService) {
        this.ownerService = ownerService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/ownersView")
    public String getOwnersView(Model model) {
        List<AllOwnerResponse> owners = ownerService.getAllOwners();
        model.addAttribute("owners", owners);
        return "ownersView"; // numele template-ului Thymeleaf fără extensia .html
    }
    @GetMapping("/vehiclesView")
    public String getVehiclesView(Model model) {
        List<AllVehicleResponse> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehiclesView";
    }
}
