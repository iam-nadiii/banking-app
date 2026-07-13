package com.banking.repository;

import com.banking.model.Transaction;
import com.banking.model.TransactionType;
import com.banking.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByTxnDateBetween(LocalDate start, LocalDate end);
    List<Transaction> findByVendor_Id(Long vendorId);
    boolean existsByVendorAndAmountAndTxnDateAndTxnTime(Vendor vendor, BigDecimal amount, LocalDate txnDate, LocalTime txnTime);
}
