package com.banking.controllers;

import com.banking.models.Search;
import com.banking.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("searches")
public class SearchController {

    private final SearchService searchService;
    private ResponseEntity<Search> ResponseEntity;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<List<Search>> getAll() {
        List<Search> searches = searchService.getAllSearches();
        return ResponseEntity.ok(searches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Search> getBySearchId(@PathVariable Long Id) {

        Optional<Search> search = searchService.getBySearchId(Id);

        if (search == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return ResponseEntity;
    }

    @GetMapping("/{vendor}")
    public ResponseEntity<Search> findByVendor(@PathVariable String vendor) {
        Optional<Search> search = searchService.findByVendor(vendor);
        if (search == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity;
    }

    @PostMapping
    public ResponseEntity<Search> addSearch(@RequestBody Search search) {
        Search created = searchService.create(search);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSearch(@PathVariable Long Id) {
        searchService.deleteSearch(Id);
        return ResponseEntity.noContent().build();
    }
}