package com.banking.service;

import com.banking.exception.DatabaseOperationException;
import com.banking.exception.DuplicateResourceException;
import com.banking.exception.InvalidInputException;
import com.banking.exception.ResourceNotFoundException;
import com.banking.model.Transaction;
import com.banking.model.TransactionType;
import com.banking.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

private final TransactionRepository transactionRepository;

public TransactionService(TransactionRepository transactionRepository){

    this.transactionRepository = transactionRepository;

}

//looking for a list of transactions in this case all of them.
public List<Transaction>getAllTransactions(){

    return transactionRepository.findAll();

    }

//looking for one transaction which should be stored in one object
public Transaction getById(Long id){

    if (id <= 0) {
        throw new InvalidInputException("Transaction ID must be a positive number");
    }
    return transactionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with this id: " + id));

}

public List<Transaction> getByType(TransactionType type) {
    if(type==null){
        throw new InvalidInputException("Transaction type can't be null");
    }
    return transactionRepository.findByType(type);
}

@Transactional
public Transaction create(Transaction transaction) {

    if (transaction.getAmount() == null) {
        throw new InvalidInputException("Transaction amount cannot be empty");
    }
    if (transaction.getType() == null) {
        throw new InvalidInputException("Transaction type cannot be empty");
    }
    if (transaction.getTxnDate() == null) {
        throw new InvalidInputException("Transaction date cannot be empty");
    }

    try {
        return transactionRepository.save(transaction);

    } catch (Exception e) {
        throw new DatabaseOperationException("Failed to save transaction", e);
    }
}

public void delete(Long id)
{
    // deletes transaction
    getById(id);  // Throws ResourceNotFound exception if not found
    transactionRepository.deleteById(id);
}

public Transaction update(Long id, Transaction transaction)
{
    // update transaction and return the updated transaction
    if (transaction.getAmount() == null) {

        throw new InvalidInputException("Transaction amount cannot be empty");

    }

    if (transaction.getType() == null) {

        throw new InvalidInputException("Transaction type cannot be empty");

    }

    if (transaction.getTxnDate() == null) {

        throw new InvalidInputException("Transaction date cannot be empty");

    }
    boolean possibleDuplicate = transactionRepository.existsByVendorAndAmountAndTxnDateAndTxnTime(
            transaction.getVendor(), transaction.getAmount(), transaction.getTxnDate(), transaction.getTxnTime());

    if (possibleDuplicate) {
        throw new DuplicateResourceException("An identical transaction already exists for this vendor, amount, and time");
    }
    Transaction existing = getById(id);

    existing.setAmount(transaction.getAmount());
    existing.setType(transaction.getType());
    existing.setTxnDate(transaction.getTxnDate());
    existing.setTxnTime(transaction.getTxnTime());
    existing.setDescription(transaction.getDescription());
    existing.setVendor(transaction.getVendor());

    try {

        return transactionRepository.save(existing);

    } catch (Exception e) {

        throw new DatabaseOperationException("Failed to update transaction", e);

    }

}

public List<Transaction> getByDateRange(LocalDate start, LocalDate end) {
    if (start == null || end == null) {
        throw new InvalidInputException("Start date and end date cannot be null");
    }
    if (start.isAfter(end)) {
        throw new InvalidInputException("Start date cannot be after end date");
    }
    return transactionRepository.findByTxnDateBetween(start, end);
}

public List<Transaction> getByVendorId(Long vendorId) {
    if (vendorId == null) {
        throw new InvalidInputException("Vendor id cannot be null");
    }
    return transactionRepository.findByVendor_Id(vendorId);
}

public List<Transaction> search(Long vendorId, TransactionType type, LocalDate startDate, LocalDate endDate) {
    try{
    return transactionRepository.search(vendorId, type, startDate, endDate);
    }catch(Exception e){
        throw new DatabaseOperationException("Failed to search transactions");
    }
}

}
