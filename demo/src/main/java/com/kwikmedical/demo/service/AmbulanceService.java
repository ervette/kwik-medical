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

    public List<Ambulance> getAvailableAmbulancesByLocation(String location) {
        return ambulanceRepository.findByLocationAndStatus(location, "Available");
    }

    private void onRescueRequest(RescueRequestEvent event) {
        List<Ambulance> availableAmbulances = getAvailableAmbulancesByLocation(event.getLocation());
        if (!availableAmbulances.isEmpty()) {
            Ambulance ambulance = availableAmbulances.get(0);
            ambulance.setStatus("In Transit");
            ambulanceRepository.save(ambulance);

            AmbulanceDispatchEvent dispatchEvent = new AmbulanceDispatchEvent(
                    ambulance.getAmbulanceId(), event.getPatientId(), event.getLocation()
            );
            eventBroker.publish(dispatchEvent);
        }
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
