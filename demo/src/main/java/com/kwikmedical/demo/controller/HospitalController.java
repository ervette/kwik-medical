package com.kwikmedical.demo.controller;

import com.kwikmedical.demo.model.RescueOperation;
import com.kwikmedical.demo.service.HospitalService;
import com.kwikmedical.demo.service.RescueOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HospitalController {

    @Autowired
    private RescueOperationService rescueOperationService;

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hospital")
    public String showHospitalScreen(@RequestParam Long hospitalId, Model model) {
        var hospital = hospitalService.getHospitalById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("Hospital not found"));
        var operations = rescueOperationService.getRescueOperationsByHospitalAndStatus(hospitalId, "Dispatched");

        model.addAttribute("hospital", hospital);
        model.addAttribute("operations", operations);

        return "hospital";
    }

    @PostMapping("/hospital/update-operation")
    public String updateOperationStatus(@RequestParam Long operationId, @RequestParam String status) {
        rescueOperationService.updateOperationStatus(operationId, status);
        return "redirect:/hospital";
    }
}

