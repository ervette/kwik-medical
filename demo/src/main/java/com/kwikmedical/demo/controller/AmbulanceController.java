package com.kwikmedical.demo.controller;

import com.kwikmedical.demo.model.Ambulance;
import com.kwikmedical.demo.model.RescueOperation;
import com.kwikmedical.demo.repository.AmbulanceRepository;
import com.kwikmedical.demo.repository.RescueOperationRepository;
import com.kwikmedical.demo.service.AmbulanceService;
import com.kwikmedical.demo.service.RescueOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ambulance")
public class AmbulanceController {

    private final RescueOperationRepository rescueOperationRepository;
    private final AmbulanceRepository ambulanceRepository;

    public AmbulanceController(RescueOperationRepository rescueOperationRepository,
                               AmbulanceRepository ambulanceRepository) {
        this.rescueOperationRepository = rescueOperationRepository;
        this.ambulanceRepository = ambulanceRepository;
    }

    @GetMapping
    public String ambulanceDashboard(Model model) {
        model.addAttribute("operations", rescueOperationRepository.findActiveRescueOperations());
        return "ambulance";
    }

    @PostMapping("/confirm-delivery")
    public String confirmDelivery(@RequestParam Long operationId, @RequestParam Long ambulanceId) {
        // Mark rescue operation as complete
        RescueOperation operation = rescueOperationRepository.findById(operationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation ID."));
        operation.setStatus("Completed");
        rescueOperationRepository.save(operation);

        // Mark ambulance as available
        Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ambulance ID."));
        ambulance.setStatus("Available");
        ambulanceRepository.save(ambulance);

        return "redirect:/ambulance";
    }
}
