package com.kwikmedical.demo.service;

import com.kwikmedical.demo.events.EventBroker;
import com.kwikmedical.demo.events.AmbulanceDispatchEvent;
import com.kwikmedical.demo.events.HospitalNotificationEvent;
import com.kwikmedical.demo.model.Hospital;
import com.kwikmedical.demo.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private EventBroker eventBroker;

    @PostConstruct
    public void init() {
        eventBroker.subscribe(AmbulanceDispatchEvent.class, this::onAmbulanceDispatched);
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Optional<Hospital> getHospitalById(Long hospitalId) {
        return hospitalRepository.findById(hospitalId);
    }

    public List<Hospital> getHospitalsByLocation(String location) {
        return hospitalRepository.findByAddressContaining(location);
    }

    private void onAmbulanceDispatched(AmbulanceDispatchEvent event) {
        List<Hospital> hospitals = getHospitalsByLocation(event.getLocation());
        if (!hospitals.isEmpty()) {
            Hospital hospital = hospitals.get(0);

            HospitalNotificationEvent notificationEvent = new HospitalNotificationEvent(
                    hospital.getHospitalId(), event.getPatientId()
            );
            eventBroker.publish(notificationEvent);
        }
    }

    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }
}
