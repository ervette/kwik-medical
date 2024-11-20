package com.kwikmedical.demo.controller;

import com.kwikmedical.demo.model.Patient;
import com.kwikmedical.demo.repository.AmbulanceRepository;
import com.kwikmedical.demo.repository.HospitalRepository;
import com.kwikmedical.demo.repository.PatientRepository;
import com.kwikmedical.demo.model.Hospital;
import com.kwikmedical.demo.model.Ambulance;
import com.kwikmedical.demo.service.PatientService;
import com.kwikmedical.demo.service.HospitalService;
import com.kwikmedical.demo.service.AmbulanceService;
import com.kwikmedical.demo.service.RescueOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/operator")
public class OperatorController {

    private final RescueOperationService rescueOperationService;
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final AmbulanceRepository ambulanceRepository;

    public OperatorController(RescueOperationService rescueOperationService, PatientRepository patientRepository,
            HospitalRepository hospitalRepository, AmbulanceRepository ambulanceRepository) {
        this.rescueOperationService = rescueOperationService;
        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
        this.ambulanceRepository = ambulanceRepository;
    }

    @GetMapping
    public String operatorDashboard(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("hospitals", hospitalRepository.findAll());
        model.addAttribute("ambulances", ambulanceRepository.findAll());
        return "operator";
    }

    @PostMapping("/create-rescue")
    public String createRescueOperation(@RequestParam String patientId,
            @RequestParam Long hospitalId,
            @RequestParam Long ambulanceId,
            @RequestParam String location,
            Model model) {
        try {
            rescueOperationService.createRescueOperation(patientId, hospitalId, ambulanceId, location);
            model.addAttribute("message", "Rescue operation created successfully.");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create rescue operation: " + e.getMessage());
        }
        return "redirect:/operator";
    }

    @PostMapping("/operator")
    public String createRescueOperation(@RequestParam String patientId,
            @RequestParam Long hospitalId,
            @RequestParam Long ambulanceId,
            @RequestParam String location) {
        rescueOperationService.createRescueOperation(patientId, hospitalId, ambulanceId, location);
        return "redirect:/operator";
    }

}
