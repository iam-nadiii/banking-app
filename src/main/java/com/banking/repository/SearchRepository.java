package com.banking.repository;

import com.banking.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

    List<Search> findByVendor(String vendor);

    // Added for scoping — same pattern as TransactionRepository.
    List<Search> findByVendorAndUserId(String vendor, Long userId);
    List<Search> findByUserId(Long userId);
    Optional<Search> findByIdAndUserId(Long id, Long userId);

}