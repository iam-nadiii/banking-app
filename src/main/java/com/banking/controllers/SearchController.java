package com.banking.controllers;

import com.banking.models.Search;
import com.banking.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("searches")
public class SearchController {

    /*Are we gonna do authorization roles?*/

    @Autowired
    private final SearchService searchService;

    @GetMapping("")
    public List<Search> search(@RequestParam(name="idk", required = false) Integer idk,
                                @RequestParam(repeat),
)

        public Search getById(){

        }

        public ResponseEntity deleteSearch(){

        }
}
