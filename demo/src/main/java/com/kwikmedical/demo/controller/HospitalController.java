package com.kwikmedical.demo.controller;

import com.kwikmedical.demo.model.RescueOperation;
import com.kwikmedical.demo.service.RescueOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HospitalController {

    @Autowired
    private RescueOperationService rescueOperationService;

    @GetMapping("/hospital")
    public String showHospitalScreen(@RequestParam Long hospitalId, Model model) {
        List<RescueOperation> operations = rescueOperationService.getRescueOperationsByStatus("Dispatched");
        model.addAttribute("operations", operations);
        return "hospital";
    }
}
