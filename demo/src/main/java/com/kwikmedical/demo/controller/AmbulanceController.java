package com.kwikmedical.demo.controller;

import com.kwikmedical.demo.service.AmbulanceService;
import com.kwikmedical.demo.service.RescueOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AmbulanceController {

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private RescueOperationService rescueOperationService;

    @GetMapping("/ambulance")
    public String showAmbulanceScreen(@RequestParam Long ambulanceId, Model model) {
        var operations = rescueOperationService.getRescueOperationsByAmbulanceAndStatus(ambulanceId, "Dispatched");
        model.addAttribute("operations", operations);
        model.addAttribute("ambulanceId", ambulanceId);
        return "ambulance";
    }

    @PostMapping("/ambulance/confirm-delivery")
    public String confirmDelivery(
            @RequestParam Long operationId,
            @RequestParam Long ambulanceId) {
        rescueOperationService.updateRescueOperationStatus(operationId, "Delivered");
        ambulanceService.updateAmbulanceStatus(ambulanceId, "Available");
        return "redirect:/ambulance?ambulanceId=" + ambulanceId;
    }

}
