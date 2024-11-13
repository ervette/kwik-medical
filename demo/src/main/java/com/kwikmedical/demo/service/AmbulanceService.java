package com.kwikmedical.demo.service;

import com.kwikmedical.demo.model.Ambulance;
import com.kwikmedical.demo.repository.AmbulanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AmbulanceService {

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    public List<Ambulance> getAllAmbulances() {
        return ambulanceRepository.findAll();
    }

    public Optional<Ambulance> getAmbulanceById(Long ambulanceId) {
        return ambulanceRepository.findById(ambulanceId);
    }

    public List<Ambulance> getAvailableAmbulancesByLocation(String location) {
        return ambulanceRepository.findByLocationAndStatus(location, "Available");
    }

    public Ambulance updateAmbulanceStatus(Long ambulanceId, String status) {
        Optional<Ambulance> optionalAmbulance = ambulanceRepository.findById(ambulanceId);
        if (optionalAmbulance.isPresent()) {
            Ambulance ambulance = optionalAmbulance.get();
            ambulance.setStatus(status);
            return ambulanceRepository.save(ambulance);
        } else {
            throw new RuntimeException("Ambulance not found with ID: " + ambulanceId);
        }
    }
}
