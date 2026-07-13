package com.banking.service;

import com.banking.models.Search;
import com.banking.repository.SearchRepository;

import java.util.List;
import java.util.Optional;

public class SearchService {

    private SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository){
        this.searchRepository = searchRepository;
    }

    public List<Search> getAllSearches(){
        return searchRepository.findAll();
    }

    public Optional<Search> findById(Long Id) {
        return searchRepository.findById(Id);
    }


    public void deleteSearch(Long Id) {
        searchRepository.deleteById(Id);
    }
}
