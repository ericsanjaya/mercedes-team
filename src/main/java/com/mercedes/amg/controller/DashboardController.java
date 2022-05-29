package com.mercedes.amg.controller;

import com.mercedes.amg.repository.VehicleStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    VehicleStatusRepository vehicleStatusRepository;

    @GetMapping()
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Taming Thymeleaf");
        model.addAttribute("scientists", List.of("Albert Einstein",
                "Niels Bohr",
                "James Clerk Maxwell"));
        return "dashboard/dashboard";
    }

    @GetMapping("/history")
    public String dashboardHistory(Model model, @RequestParam String vehicleId, @RequestParam int page) {
        var pg = PageRequest.of(Math.max(page - 1, 0), 50);
        var result = vehicleStatusRepository.findByVehicleId(vehicleId, pg);

        var haveNext = result.size() >= 50;

        model.addAttribute("data", result);
        model.addAttribute("haveNext", haveNext);
        model.addAttribute("page", page);
        model.addAttribute("path", "/dashboard/history");
        model.addAttribute("vehicleId", vehicleId);
        return "dashboard/history";
    }
}
