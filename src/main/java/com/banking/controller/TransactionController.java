package com.banking.controller;

import com.banking.model.Transaction;
import com.banking.model.TransactionType;
import com.banking.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
private final TransactionService transactionService;

public TransactionController(TransactionService transactionService){
    this.transactionService=transactionService;
}

//Fetch transactions belonging strictly to ONE user
@GetMapping("/user/{userId}")
public ResponseEntity<List<Transaction>> getByUserId(@PathVariable Long userId) {
    List<Transaction> transactions = transactionService.getByUserId(userId);
    return ResponseEntity.ok(transactions);
}

//Secured: Only gets the transaction if it belongs to this specific user context
@GetMapping("/{id}/user/{userId}")
public ResponseEntity<Transaction> getByIdAndUserId(@PathVariable Long id, @PathVariable Long userId){
    Transaction transaction = transactionService.getByIdAndUserId(id, userId);
    return ResponseEntity.ok(transaction);
}

@GetMapping
    public ResponseEntity<List<Transaction>>getAll(){
    List<Transaction>transactions=transactionService.getAllTransactions();
    return ResponseEntity.ok(transactions);
}

@GetMapping("/{id}")
   public ResponseEntity<Transaction>getById(@PathVariable Long id){
    Transaction transaction = transactionService.getById(id);
    return ResponseEntity.ok(transaction);
}

@GetMapping("/vendor/{vendorId}")
public ResponseEntity<List<Transaction>> getByVendorId(@PathVariable Long vendorId) {

    List<Transaction> transactions = transactionService.getByVendorId(vendorId);
    return ResponseEntity.ok(transactions);
}

@GetMapping("/date-range")
public ResponseEntity<List<Transaction>> getByDateRange(
        @RequestParam LocalDate start,
        @RequestParam LocalDate end) {

    List<Transaction> transactions = transactionService.getByDateRange(start, end);
    return ResponseEntity.ok(transactions);
}

@GetMapping("/type/{type}")
public ResponseEntity<List<Transaction>> getByType(@PathVariable TransactionType type) {

    List<Transaction> transactions = transactionService.getByType(type);
    return ResponseEntity.ok(transactions);
}

@GetMapping("/search")
public ResponseEntity<List<Transaction>> search(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) Long vendorId,
        @RequestParam(required = false) TransactionType type,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate) {

    List<Transaction> transactions = transactionService.search(userId, vendorId, type, startDate, endDate);
    return ResponseEntity.ok(transactions);
}

@PostMapping
    public ResponseEntity<Transaction>addTransaction(@RequestBody Transaction transaction){
    Transaction created = transactionService.create(transaction);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}

@PutMapping("/{id}")
    public ResponseEntity<Transaction>updateTransaction(@PathVariable Long id,@RequestBody Transaction transaction){
    Transaction updated = transactionService.update(id,transaction);
    return ResponseEntity.ok(updated);
}


@DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteTransaction(@PathVariable Long id){
    transactionService.delete(id);
    return ResponseEntity.noContent().build();
}

}
