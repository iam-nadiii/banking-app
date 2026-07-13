package com.banking.service;

import com.banking.models.Search;
import com.banking.repository.SearchRepository;

import java.util.List;
import java.util.Optional;

public class SearchService {

    private SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<Search> getAllSearches() {
        return searchRepository.findAll();
    }

    public Optional<Search> findById(Long Id) {
        return searchRepository.findById(Id);
    }

    public Optional<Search> findByVendor(String vendor) {
        return searchRepository.findByVendor(vendor);
    }

    public Search create(Search search) {
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
