package com.kwikmedical.demo.repository;

import com.kwikmedical.demo.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    List<Patient> findByLocation(String location);
    List<Patient> findByPatientId(String patientId);
}
