package com.banking.service;

import com.banking.exception.InvalidInputException;
import com.banking.exception.ResourceNotFoundException;
import com.banking.model.Search;
import com.banking.repository.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<Search> getAllSearches() {
        return searchRepository.findAll();
    }

    public Search getBySearchId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidInputException("Search ID Invalid (Hint: Number must be positive)");
        }
        return searchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Search not found with this id: " + id));
    }

    // Added for scoping — mirrors TransactionService.getByUserId /
    // getByIdAndUserId exactly.
    public List<Search> getByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new InvalidInputException("User ID must be a positive number");
        }
        return searchRepository.findByUserId(userId);
    }

    public Search getByIdAndUserId(Long id, Long userId) {
        if (id == null || id <= 0 || userId == null) {
            throw new InvalidInputException("Invalid search ID or user ID");
        }
        return searchRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Search with id: " + id + " not found for user ID: " + userId));
    }

    public List<Search> findByVendor(String vendor) {
        if (vendor == null || vendor.isBlank()) {
            throw new InvalidInputException("Vendor cannot be empty");
        }
        return searchRepository.findByVendor(vendor);
    }

    public List<Search> findByVendorAndUserId(String vendor, Long userId) {
        if (vendor == null || vendor.isBlank()) {
            throw new InvalidInputException("Vendor cannot be empty");
        }
        if (userId == null || userId <= 0) {
            throw new InvalidInputException("User ID must be a positive number");
        }
        return searchRepository.findByVendorAndUserId(vendor, userId);
    }

    public Search create(Search search) {
        // Note: no ID check here — new searches must NOT have an ID yet.
        // The database assigns it on insert (GenerationType.IDENTITY on Search.Id).
        // Rejecting null IDs here would reject every legitimate creation attempt.

        if (search.getSearchDate() == null) {
            throw new InvalidInputException("Search date cannot be empty");
        }

        // FIX: the old TODO here is resolved — SearchController now calls
        // search.setUser(currentUser()) before this runs, same as
        // TransactionController does for transactions. This check confirms
        // that actually happened, rather than silently saving an
        // unattributed search that (now that user_id is nullable) would
        // insert fine but never show up for anyone, including its creator.
        if (search.getUser() == null || search.getUser().getId() == null) {
            throw new InvalidInputException("Search must be associated with a user");
        }

        return searchRepository.save(search);
    }

    public void deleteSearch(Long id) {
        if (!searchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Search not found with this id: " + id);
        }
        searchRepository.deleteById(id);
    }
}