package com.kwikmedical.demo.repository;

import com.kwikmedical.demo.model.RescueOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RescueOperationRepository extends JpaRepository<RescueOperation, Long> {
    @Query("SELECT r FROM RescueOperation r WHERE r.status = 'Dispatched'")
    List<RescueOperation> findActiveRescueOperations();

    List<RescueOperation> findByStatus(String status);
}
