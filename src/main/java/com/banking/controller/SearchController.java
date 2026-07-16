package com.banking.controller;

import com.banking.exception.ResourceNotFoundException;
import com.banking.model.Search;
import com.banking.model.User;
import com.banking.security.SecurityUtils;
import com.banking.service.SearchService;
import com.banking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/searches")
public class SearchController {

    private final SearchService searchService;
    private final UserService userService;

    public SearchController(SearchService searchService, UserService userService) {
        this.searchService = searchService;
        this.userService = userService;
    }

    // ---- identity helpers, same pattern as TransactionController ----

    private User currentUser() {
        String username = SecurityUtils.getCurrentUsername()
                .orElseThrow(() -> new ResourceNotFoundException("Not authenticated"));
        return userService.getByUserName(username);
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public ResponseEntity<List<Search>> getAll() {
        // FIX: this was searchService.getAllSearches() unconditionally —
        // every user saw every other user's saved searches. Now scoped
        // the same way GET /transactions is.
        List<Search> searches = isAdmin()
                ? searchService.getAllSearches()
                : searchService.getByUserId(currentUser().getId());
        return ResponseEntity.ok(searches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Search> getBySearchId(@PathVariable("id") Long id) {
        Search search = isAdmin()
                ? searchService.getBySearchId(id)
                : searchService.getByIdAndUserId(id, currentUser().getId());
        return ResponseEntity.ok(search);
    }

    @GetMapping("/vendor/{vendor}")
    public ResponseEntity<List<Search>> findByVendor(@PathVariable("vendor") String vendor) {
        List<Search> searches = isAdmin()
                ? searchService.findByVendor(vendor)
                : searchService.findByVendorAndUserId(vendor, currentUser().getId());
        return ResponseEntity.ok(searches);
    }

    @PostMapping
    public ResponseEntity<Search> addSearch(@RequestBody Search search) {
        // FIX: always the caller's own identity — never whatever (if
        // anything) the client set on the incoming JSON body. Same pattern
        // as TransactionController.addTransaction().
        search.setUser(currentUser());
        Search created = searchService.create(search);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSearch(@PathVariable("id") Long id) {
        // FIX: this deleted by ID alone with zero ownership check before —
        // meaning any logged-in user could delete anyone else's saved
        // search. Now verifies ownership (or admin) first, same as
        // TransactionController.deleteTransaction().
        if (isAdmin()) {
            searchService.getBySearchId(id); // 404s cleanly if it doesn't exist
        } else {
            searchService.getByIdAndUserId(id, currentUser().getId()); // 404s if it's not theirs, without confirming it exists for someone else
        }
        searchService.deleteSearch(id);
        return ResponseEntity.noContent().build();
    }
}