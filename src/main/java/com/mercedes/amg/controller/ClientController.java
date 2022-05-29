package com.mercedes.amg.controller;

import com.mercedes.amg.repository.VehicleStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    VehicleStatusRepository vehicleStatusRepository;

    @GetMapping()
    public String client(Model model) {
        return "client/client";
    }

    @GetMapping("/history")
    public String clientResult(Model model, @RequestParam String vehicleId, @RequestParam int page) {
        Calendar currentTimeNow = Calendar.getInstance();
        currentTimeNow.add(Calendar.MINUTE, -5);
        Date dt = currentTimeNow.getTime();
        var pg = PageRequest.of(page, 50, Sort.by("date_time").descending());
        var result = vehicleStatusRepository.findByDateTimeIsAfter(vehicleId, pg);

        var haveNext = result.size() >= 50;

        model.addAttribute("data", result);
        model.addAttribute("haveNext", haveNext);
        model.addAttribute("page", page);
        model.addAttribute("path", "/client/history");
        model.addAttribute("vehicleId", vehicleId);
        return "client/history";
    }
}
