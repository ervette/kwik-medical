package com.kwikmedical.demo.controller;

import com.kwikmedical.demo.model.Patient;
import com.kwikmedical.demo.model.Hospital;
import com.kwikmedical.demo.model.Ambulance;
import com.kwikmedical.demo.model.RescueOperation;
import com.kwikmedical.demo.service.PatientService;
import com.kwikmedical.demo.service.HospitalService;
import com.kwikmedical.demo.service.AmbulanceService;
import com.kwikmedical.demo.service.RescueOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OperatorController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private RescueOperationService rescueOperationService;

    @GetMapping("/operator")
    public String showOperatorScreen(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        List<Ambulance> ambulances = ambulanceService.getAvailableAmbulancesByLocation("Edinburgh"); // Assuming location is Edinburgh

        model.addAttribute("patients", patients);
        model.addAttribute("hospitals", hospitals);
        model.addAttribute("ambulances", ambulances);
        return "operator";
    }

    @PostMapping("/operator")
    public String createRescueOperation(
            @RequestParam String patientId,
            @RequestParam Long hospitalId,
            @RequestParam Long ambulanceId,
            @RequestParam String location) {
        
        rescueOperationService.createRescueOperation(patientId, hospitalId, ambulanceId, location);
        return "redirect:/operator";
    }
}
