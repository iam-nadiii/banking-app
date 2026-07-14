package com.banking.repository;

import com.banking.model.Transaction;
import com.banking.model.TransactionType;
import com.banking.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByTxnDateBetween(LocalDate start, LocalDate end);
    List<Transaction> findByVendor_Id(Long vendorId);
    boolean existsByVendorAndAmountAndTxnDateAndTxnTime(Vendor vendor, BigDecimal amount, LocalDate txnDate, LocalTime txnTime);
    @Query("SELECT t FROM Transaction t WHERE " +
            "(:vendorId IS NULL OR t.vendor.id = :vendorId) AND " +
            "(:type IS NULL OR t.type = :type) AND " +
            "(:startDate IS NULL OR t.txnDate >= :startDate) AND " +
            "(:endDate IS NULL OR t.txnDate <= :endDate)")
    List<Transaction> search(@Param("vendorId") Long vendorId,
                             @Param("type") TransactionType type,
                             @Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate);
}
