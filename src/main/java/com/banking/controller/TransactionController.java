package com.banking.controller;

import com.banking.exception.ResourceNotFoundException;
import com.banking.model.Transaction;
import com.banking.model.TransactionType;
import com.banking.model.User;
import com.banking.security.SecurityUtils;
import com.banking.service.TransactionService;
import com.banking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    // ---- identity helpers: every endpoint below goes through these rather
    // than trusting anything the client claims about who they are ----

    private User currentUser() {
        String username = SecurityUtils.getCurrentUsername()
                .orElseThrow(() -> new ResourceNotFoundException("Not authenticated"));
        return userService.getByUserName(username);
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    // ---- admin-only lookups by an arbitrary user's ID. Regular users get
    // their own data through the plain endpoints below instead — these two
    // only make sense as admin tools now that ownership is enforced
    // everywhere else. ----

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getByUserId(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/user/{userId}")
    public ResponseEntity<Transaction> getByIdAndUserId(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getByIdAndUserId(id, userId));
    }

    // ---- everyone's everyday endpoints: scoped to the caller unless admin ----

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll() {
        List<Transaction> transactions = isAdmin()
                ? transactionService.getAllTransactions()
                : transactionService.getByUserId(currentUser().getId());
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Long id) {
        Transaction transaction = isAdmin()
                ? transactionService.getById(id)
                : transactionService.getByIdAndUserId(id, currentUser().getId());
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Transaction>> getByVendorId(@PathVariable Long vendorId) {
        List<Transaction> transactions = isAdmin()
                ? transactionService.getByVendorId(vendorId)
                : transactionService.search(currentUser().getId(), vendorId, null, null, null);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Transaction>> getByDateRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        List<Transaction> transactions = isAdmin()
                ? transactionService.getByDateRange(start, end)
                : transactionService.search(currentUser().getId(), null, null, start, end);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> getByType(@PathVariable TransactionType type) {
        List<Transaction> transactions = isAdmin()
                ? transactionService.getByType(type)
                : transactionService.search(currentUser().getId(), null, type, null, null);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Transaction>> search(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long vendorId,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        // The client-supplied userId is only honored for admins. Everyone
        // else gets forced to their own ID regardless of what was in the
        // query string — this was the actual vulnerability: previously
        // *anyone* could pass any userId here and read that user's data.
        Long effectiveUserId = isAdmin() ? userId : currentUser().getId();

        List<Transaction> transactions = transactionService.search(effectiveUserId, vendorId, type, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        // Always the caller's own identity — never whatever (if anything)
        // the client set on the incoming JSON body.
        transaction.setUser(currentUser());
        Transaction created = transactionService.create(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        // Fetch the existing record first — this doubles as the
        // authorization check (a 404 here means "not yours", without
        // revealing whether the transaction exists for someone else).
        // Then force the update onto the transaction's REAL existing
        // owner, never onto whatever the client's request body claims.
        // (TransactionService.update() calls transaction.getUser().getId()
        // unconditionally with no null-check on getUser() itself — passing
        // a transaction with no user set here would NPE before any of its
        // validation messages even run, so this also avoids that.)
        Transaction existing = isAdmin()
                ? transactionService.getById(id)
                : transactionService.getByIdAndUserId(id, currentUser().getId());

        transaction.setUser(existing.getUser());
        Transaction updated = transactionService.update(id, transaction);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        // Verifies ownership (or admin) before deleting anything — the
        // original version deleted by ID alone with no check at all.
        if (isAdmin()) {
            transactionService.getById(id); // 404s cleanly if it doesn't exist
        } else {
            transactionService.getByIdAndUserId(id, currentUser().getId()); // 404s if it's not theirs too — doesn't reveal it exists
        }
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}