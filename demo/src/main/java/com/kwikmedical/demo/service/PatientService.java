package com.kwikmedical.demo.service;

import com.kwikmedical.demo.model.Patient;
import com.kwikmedical.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(String patientId) {
        return patientRepository.findById(patientId);
    }

    public List<Patient> getPatientsByLocation(String location) {
        return patientRepository.findByLocation(location);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }
}
