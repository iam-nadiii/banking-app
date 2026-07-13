package com.banking.service;

import com.banking.exception.InvalidInputException;
import com.banking.exception.ResourceNotFoundException;
import com.banking.models.Search;
import com.banking.repository.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<Search> getAllSearches() {
        return searchRepository.findAll();
    }

    public Optional<Search> getBySearchId(Long Id) {

        if (Id <= 0){
            throw new InvalidInputException("Search ID Invalid (Hint: Number must be positive)");
        }
        return Optional.of(searchRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Search not found with this id: " + Id)));
    }

    public Optional<Search> findByVendor(String vendor) {
        if(vendor==null){
            throw new InvalidInputException("Transaction type cannot be null");
        }
        return searchRepository.findByVendor(vendor);
    }

    public Search create(Search search) {

        if(search.getId() == null){
        throw new InvalidInputException("Search ID cannot be empty");
        }

        if(search.getSearchDate() == null){
        throw new InvalidInputException("Search date cannot be empty");
        }

        return searchRepository.save(search);
    }

    public void deleteSearch(Long Id) {
        searchRepository.deleteById(Id);
    }
}
