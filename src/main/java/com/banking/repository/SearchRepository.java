package com.banking.repository;

import com.banking.models.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long>{

    Search getById(Long Id);

    Optional<Search> findByVendor(String vendor);

    Search deleteSearch(Long Id);

    Search create(Search search);

    Search update(Long Id, Search search);

}
