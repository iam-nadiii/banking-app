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

    public List<Search> findByVendor(String vendor) {
        if (vendor == null || vendor.isBlank()) {
            throw new InvalidInputException("Vendor cannot be empty");
        }
        return searchRepository.findByVendor(vendor);
    }

    public Search create(Search search) {
        // Note: no ID check here — new searches must NOT have an ID yet.
        // The database assigns it on insert (GenerationType.IDENTITY on Search.Id).
        // Rejecting null IDs here would reject every legitimate creation attempt.

        if (search.getSearchDate() == null) {
            throw new InvalidInputException("Search date cannot be empty");
        }

        // TODO: `searches.user_id` is NOT NULL in the database, but Search.java has
        // no userId field and nothing here sets one. Until the team decides how user
        // association works (auth system? request param?), calls to this method will
        // fail at the database level with a constraint violation, not a clean
        // InvalidInputException. Resolve before this goes live.

        return searchRepository.save(search);
    }

    public void deleteSearch(Long id) {
        if (!searchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Search not found with this id: " + id);
        }
        searchRepository.deleteById(id);
    }
}