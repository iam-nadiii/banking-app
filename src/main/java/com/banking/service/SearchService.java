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

    public Optional<Search> findById(Long Id) {

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

    public Search update(Long Id, Search search) {
        Search existing = searchRepository.findById(Id).orElseThrow();
        existing.setId(search.getId());
        existing.setEndDate(search.getEndDate());
        existing.setSearchDate(search.getSearchDate());
        existing.setSearchTime(search.getSearchTime());
        existing.setStartDate(search.getStartDate());
        existing.setDescription(search.getDescription());
        existing.setVendor(search.getVendor());
        existing.setMinAmount(search.getMinAmount());
        existing.setMaxAmount(search.getMaxAmount());
        return searchRepository.save(existing);
    }

    public void deleteSearch(Long Id) {
        searchRepository.deleteById(Id);
    }
}
