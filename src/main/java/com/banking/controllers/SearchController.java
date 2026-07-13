package com.banking.controllers;

import com.banking.models.Search;
import com.banking.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
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

        public SearchController(SearchService searchService){
                this.searchService=searchService;
        }

//    @Autowired
//    private final SearchService searchService;

//    @GetMapping("")
//    public List<Search> search(@RequestParam(name="idk", required = false) Integer idk,
//                                @RequestParam(repeat),
//)

        @PutMapping("{Id}")
        public ResponseEntity<Search>updateSearch(@PathVariable Long Id, @RequestBody Search search){
                Search updated = searchService.update(Id,search);
                return ResponseEntity.ok(updated);
        }

        @GetMapping
        public ResponseEntity<List<Search>>getAll(){
                List<Search>searches=searchService.getAllSearches();
                return ResponseEntity.ok(searches);
        }

        @GetMapping("{id}")
        public ResponseEntity<Search>getById(@PathVariable Long Id){

        Optional<Search> search = searchService.findById(Id);

        if (search == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return ResponseEntity;
        }

        @PostMapping
        public ResponseEntity<Search>addSearch(@RequestBody Search search){
                Search created = searchService.create(search);
                return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }

        @DeleteMapping("{id}")
        public ResponseEntity<Void> deleteSearch(@PathVariable Long Id){
        searchService.deleteSearch(Id);
        return ResponseEntity.noContent().build();
        }
}
