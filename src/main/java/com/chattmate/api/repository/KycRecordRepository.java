package com.chattmate.api.repository;

import com.chattmate.api.model.entity.KycRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * KYC Record Repository - Data access for KYC verification records
 */
@Repository
public interface KycRecordRepository extends JpaRepository<KycRecord, String> {
    
    Optional<KycRecord> findByUserId(String userId);
    
    @Query("SELECT k FROM KycRecord k WHERE k.status = 'PENDING' ORDER BY k.createdAt ASC")
    List<KycRecord> findPendingRecords();
    
    @Query("SELECT k FROM KycRecord k WHERE k.status = :status")
    List<KycRecord> findByStatus(@Param("status") String status);
    
    @Query("SELECT k FROM KycRecord k WHERE k.status = 'APPROVED'")
    List<KycRecord> findApprovedRecords();
    
    long countByStatus(String status);
}
