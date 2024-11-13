package com.kwikmedical.demo.repository;

import com.kwikmedical.demo.model.RescueOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RescueOperationRepository extends JpaRepository<RescueOperation, Long> {
    List<RescueOperation> findByStatus(String status);
    List<RescueOperation> findByPatient_PatientId(String patientId);
}
