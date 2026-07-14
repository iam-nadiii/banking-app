package com.banking.controller;

import com.banking.model.Search;
import com.banking.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/searches")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<List<Search>> getAll() {
        List<Search> searches = searchService.getAllSearches();
        return ResponseEntity.ok(searches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Search> getBySearchId(@PathVariable("id") Long id) {
        Search search = searchService.getBySearchId(id);
        return ResponseEntity.ok(search);
    }

    @GetMapping("/vendor/{vendor}")
    public ResponseEntity<List<Search>> findByVendor(@PathVariable("vendor") String vendor) {
        List<Search> searches = searchService.findByVendor(vendor);
        return ResponseEntity.ok(searches);
    }

    @PostMapping
    public ResponseEntity<Search> addSearch(@RequestBody Search search) {
        Search created = searchService.create(search);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSearch(@PathVariable("id") Long id) {
        searchService.deleteSearch(id);
        return ResponseEntity.noContent().build();
    }
}