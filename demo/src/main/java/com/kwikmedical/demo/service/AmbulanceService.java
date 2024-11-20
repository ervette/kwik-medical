package com.kwikmedical.demo.service;

import com.kwikmedical.demo.events.EventBroker;
import com.kwikmedical.demo.events.RescueRequestEvent;
import com.kwikmedical.demo.events.AmbulanceDispatchEvent;
import com.kwikmedical.demo.model.Ambulance;
import com.kwikmedical.demo.repository.AmbulanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class AmbulanceService {

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private EventBroker eventBroker;

    @PostConstruct
    public void init() {
        eventBroker.subscribe(RescueRequestEvent.class, this::onRescueRequest);
    }

    public List<Ambulance> getAllAmbulances() {
        return ambulanceRepository.findAll();
    }

    public Optional<Ambulance> getAmbulanceById(Long ambulanceId) {
        return ambulanceRepository.findById(ambulanceId);
    }

    public List<Ambulance> getAvailableAmbulances() {
        return ambulanceRepository.findByStatus("Available");
    }

    public void updateAmbulanceStatus(Long ambulanceId, String status) {
        Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                .orElseThrow(() -> new IllegalArgumentException("Ambulance with ID " + ambulanceId + " not found"));
                
        ambulance.setStatus(status);
        ambulanceRepository.save(ambulance);
    
        System.out.println("Updated ambulance ID: " + ambulanceId + " to status: " + status);
    }
    
    
    private void onRescueRequest(RescueRequestEvent event) {
        List<Ambulance> availableAmbulances = getAvailableAmbulances();
        if (!availableAmbulances.isEmpty()) {
            Ambulance ambulance = availableAmbulances.get(0);
            ambulance.setStatus("In Transit");
            ambulanceRepository.save(ambulance);

            AmbulanceDispatchEvent dispatchEvent = new AmbulanceDispatchEvent(
                    ambulance.getAmbulanceId(), event.getPatientId(), event.getLocation());
            eventBroker.publish(dispatchEvent);
        }
    }

}
