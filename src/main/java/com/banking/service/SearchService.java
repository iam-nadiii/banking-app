package com.banking.service;

import com.banking.models.Search;
import com.banking.repository.SearchRepository;

import java.util.List;

public class SearchService {

    private SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository){
        this.searchRepository = searchRepository;
    }

    public List<Search> getAllSearches(){
        return searchRepository.findAll();
    }

    public List<Search> /*Make one to get singular and another to delete*/

}
