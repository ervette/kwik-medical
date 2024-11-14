package com.kwikmedical.demo.service;

import com.kwikmedical.demo.events.EventBroker;
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

import java.util.List;
import java.util.Optional;

@Service
public class RescueOperationService {

    @Autowired
    private RescueOperationRepository rescueOperationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private EventBroker eventBroker;

    public List<RescueOperation> getAllRescueOperations() {
        return rescueOperationRepository.findAll();
    }

    public List<RescueOperation> getRescueOperationsByStatus(String status) {
        return rescueOperationRepository.findByStatus(status);
    }

    public List<RescueOperation> getRescueOperationsByPatientId(String patientId) {
        return rescueOperationRepository.findByPatient_PatientId(patientId);
    }

    public RescueOperation createRescueOperation(String patientId, Long hospitalId, Long ambulanceId, String location) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        Optional<Ambulance> ambulance = ambulanceRepository.findById(ambulanceId);

        if (patient.isPresent() && hospital.isPresent() && ambulance.isPresent()) {
            RescueOperation operation = new RescueOperation();
            operation.setPatient(patient.get());
            operation.setHospital(hospital.get());
            operation.setAmbulance(ambulance.get());
            operation.setLocation(location);
            operation.setStatus("Dispatched");

            ambulance.get().setStatus("In Transit");
            ambulanceRepository.save(ambulance.get());

            rescueOperationRepository.save(operation);

            RescueRequestEvent event = new RescueRequestEvent(patientId, location);
            eventBroker.publish(event);

            return operation;
        } else {
            throw new RuntimeException("Invalid patient, hospital, or ambulance ID provided.");
        }
    }

    public RescueOperation updateRescueOperationStatus(Long operationId, String status) {
        Optional<RescueOperation> operation = rescueOperationRepository.findById(operationId);
        if (operation.isPresent()) {
            RescueOperation rescueOperation = operation.get();
            rescueOperation.setStatus(status);
            return rescueOperationRepository.save(rescueOperation);
        } else {
            throw new RuntimeException("Rescue operation not found with ID: " + operationId);
        }
    }
}
