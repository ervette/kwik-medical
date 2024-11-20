package com.kwikmedical.demo.service;

import com.kwikmedical.demo.events.AmbulanceStatusUpdatedEvent;
import com.kwikmedical.demo.events.EventBroker;
import com.kwikmedical.demo.events.RescueOperationUpdatedEvent;
import com.kwikmedical.demo.events.RescueRequestEvent;
import com.kwikmedical.demo.model.Ambulance;
import com.kwikmedical.demo.model.Hospital;
import com.kwikmedical.demo.model.Patient;
import com.kwikmedical.demo.model.RescueOperation;
import com.kwikmedical.demo.repository.AmbulanceRepository;
import com.kwikmedical.demo.repository.HospitalRepository;
import com.kwikmedical.demo.repository.PatientRepository;
import com.kwikmedical.demo.repository.RescueOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RescueOperationService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private RescueOperationRepository rescueOperationRepository;

    public List<RescueOperation> getRescueOperationsByStatus(String status) {
        return rescueOperationRepository.findByStatus(status);
    }

    public void createRescueOperation(String patientId, Long hospitalId, Long ambulanceId, String location) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow();

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow();

        Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                .orElseThrow();

        RescueOperation operation = new RescueOperation();
        operation.setPatient(patient);
        operation.setHospital(hospital);
        operation.setAmbulance(ambulance);
        operation.setLocation(location);
        operation.setStatus("Dispatched");
        operation.setCreatedAt(LocalDateTime.now());

        rescueOperationRepository.save(operation);

        // Update ambulance status
        ambulance.setStatus("In Transit");
        ambulanceRepository.save(ambulance);
    }
}
