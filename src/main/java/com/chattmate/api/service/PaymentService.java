package com.chattmate.api.service;

import com.chattmate.api.model.entity.Transaction;
import com.chattmate.api.model.entity.User;
import com.chattmate.api.model.enum.TransactionType;
import com.chattmate.api.model.enum.TransactionStatus;
import com.chattmate.api.repository.TransactionRepository;
import com.chattmate.api.repository.UserRepository;
import com.chattmate.api.exception.ResourceNotFoundException;
import com.chattmate.api.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Payment Service
 * Handles token purchases, transfers, and wallet management
 */
@Service
@Transactional
public class PaymentService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Process token purchase (cash-in)
     */
    public Transaction processCashIn(String userId, int tokenAmount) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        
        User user = userOpt.get();
        
        // Create transaction record
        Transaction transaction = new Transaction(userId, TransactionType.DEPOSIT, tokenAmount);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setCompletedAt(LocalDateTime.now());
        transaction.setReferenceId(UUID.randomUUID().toString());
        transaction.setDescription("Token purchase: " + tokenAmount + " tokens");
        
        // Update user token balance
        user.setTokenBalance(user.getTokenBalance() + tokenAmount);
        user.setHasDeposited(true);
        
        userRepository.save(user);
        return transactionRepository.save(transaction);
    }
    
    /**
     * Get transaction history for user
     */
    public List<Transaction> getUserTransactions(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        
        return transactionRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    /**
     * Get user's total deposited amount
     */
    public Integer getUserTotalDeposits(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        
        Integer total = transactionRepository.getTotalDepositsForUser(userId);
        return total != null ? total : 0;
    }
    
    /**
     * Deduct tokens from user (for spending)
     */
    public void deductTokens(String userId, int tokenAmount) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        
        User user = userOpt.get();
        if (user.getTokenBalance() < tokenAmount) {
            throw new ValidationException("Insufficient token balance");
        }
        
        user.setTokenBalance(user.getTokenBalance() - tokenAmount);
        userRepository.save(user);
        
        // Record transaction
        Transaction transaction = new Transaction(userId, TransactionType.WITHDRAWAL, tokenAmount);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setCompletedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
    
    /**
     * Transfer tokens between users
     */
    public void transferTokens(String senderId, String recipientId, int tokenAmount) {
        if (senderId.equals(recipientId)) {
            throw new ValidationException("Cannot transfer tokens to yourself");
        }
        
        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> recipientOpt = userRepository.findById(recipientId);
        
        if (senderOpt.isEmpty() || recipientOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        
        User sender = senderOpt.get();
        User recipient = recipientOpt.get();
        
        if (sender.getTokenBalance() < tokenAmount) {
            throw new ValidationException("Insufficient tokens to transfer");
        }
        
        sender.setTokenBalance(sender.getTokenBalance() - tokenAmount);
        recipient.setTokenBalance(recipient.getTokenBalance() + tokenAmount);
        
        userRepository.save(sender);
        userRepository.save(recipient);
        
        // Record transaction
        Transaction transaction = new Transaction(senderId, TransactionType.TRANSFER, tokenAmount);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setCompletedAt(LocalDateTime.now());
        transaction.setDescription("Transfer to: " + recipientId);
        transactionRepository.save(transaction);
    }
}
