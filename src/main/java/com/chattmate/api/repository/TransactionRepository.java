package com.chattmate.api.repository;

import com.chattmate.api.model.entity.Transaction;
import com.chattmate.api.model.enum.TransactionType;
import com.chattmate.api.model.enum.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Transaction Repository - Data access for payment transactions
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    
    List<Transaction> findByUserId(String userId);
    
    List<Transaction> findByUserIdOrderByCreatedAtDesc(String userId);
    
    Optional<Transaction> findByReferenceId(String referenceId);
    
    List<Transaction> findByUserIdAndType(String userId, TransactionType type);
    
    List<Transaction> findByUserIdAndStatus(String userId, TransactionStatus status);
    
    @Query("SELECT t FROM Transaction t WHERE t.userId = :userId AND t.type = :type AND t.status = 'SUCCESS' ORDER BY t.createdAt DESC")
    List<Transaction> findSuccessfulTransactionsByUserAndType(@Param("userId") String userId, @Param("type") TransactionType type);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.userId = :userId AND t.type = 'DEPOSIT' AND t.status = 'SUCCESS'")
    Integer getTotalDepositsForUser(@Param("userId") String userId);
    
    List<Transaction> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
