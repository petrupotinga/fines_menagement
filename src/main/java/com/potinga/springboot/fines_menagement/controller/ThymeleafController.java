package com.potinga.springboot.fines_menagement.controller;

import com.potinga.springboot.fines_menagement.dto.rest.owner.AllOwnerResponse;
import com.potinga.springboot.fines_menagement.service.OwnerService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SpringBootApplication
@Controller
public class ThymeleafController {
    private final OwnerService ownerService;

    public ThymeleafController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/view-all-owners")
    public String getOwnersView(Model model) {
        List<AllOwnerResponse> owners = ownerService.getAllOwners();
        model.addAttribute("owners", owners);
        return "ownersView"; // numele template-ului Thymeleaf fără extensia .html
    }
}
