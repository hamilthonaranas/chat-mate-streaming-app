package com.chattmate.api.service;

import com.chattmate.api.model.entity.KycRecord;
import com.chattmate.api.model.entity.User;
import com.chattmate.api.repository.KycRecordRepository;
import com.chattmate.api.repository.UserRepository;
import com.chattmate.api.exception.ResourceNotFoundException;
import com.chattmate.api.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * KYC Service
 * Handles user KYC verification and compliance
 */
@Service
@Transactional
public class KycService {
    
    @Autowired
    private KycRecordRepository kycRecordRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Initiate KYC verification for user
     */
    public KycRecord initiateKyc(String userId, KycRecord kycData) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        
        Optional<KycRecord> existingOpt = kycRecordRepository.findByUserId(userId);
        
        KycRecord kycRecord;
        if (existingOpt.isPresent()) {
            kycRecord = existingOpt.get();
            kycRecord.setStatus("PENDING");
        } else {
            kycRecord = new KycRecord(userId);
        }
        
        kycRecord.setFirstName(kycData.getFirstName());
        kycRecord.setLastName(kycData.getLastName());
        kycRecord.setDateOfBirth(kycData.getDateOfBirth());
        kycRecord.setIdType(kycData.getIdType());
        kycRecord.setIdNumber(kycData.getIdNumber());
        kycRecord.setCountry(kycData.getCountry());
        kycRecord.setAddress(kycData.getAddress());
        
        return kycRecordRepository.save(kycRecord);
    }
    
    /**
     * Get KYC status for user
     */
    public KycRecord getKycStatus(String userId) {
        return kycRecordRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("KYC record not found for user: " + userId));
    }
    
    /**
     * Approve KYC for user (called from webhook)
     */
    public KycRecord approveKyc(String userId, String providerReferenceId) {
        Optional<KycRecord> kycOpt = kycRecordRepository.findByUserId(userId);
        if (kycOpt.isEmpty()) {
            throw new ResourceNotFoundException("KYC record not found");
        }
        
        KycRecord kyc = kycOpt.get();
        kyc.setStatus("APPROVED");
        kyc.setVerifiedAt(LocalDateTime.now());
        kyc.setProviderReferenceId(providerReferenceId);
        
        kycRecordRepository.save(kyc);
        
        // Update user verified flag
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setVerifiedAdult(true);
            userRepository.save(user);
        }
        
        return kyc;
    }
    
    /**
     * Reject KYC for user
     */
    public KycRecord rejectKyc(String userId, String reason) {
        Optional<KycRecord> kycOpt = kycRecordRepository.findByUserId(userId);
        if (kycOpt.isEmpty()) {
            throw new ResourceNotFoundException("KYC record not found");
        }
        
        KycRecord kyc = kycOpt.get();
        kyc.setStatus("REJECTED");
        kyc.setRejectionReason(reason);
        
        kycRecordRepository.save(kyc);
        
        // Update user verified flag
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setVerifiedAdult(false);
            userRepository.save(user);
        }
        
        return kyc;
    }
}
