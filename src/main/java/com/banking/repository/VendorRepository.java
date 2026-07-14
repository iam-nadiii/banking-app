package com.banking.repository;

import com.banking.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Optional<Vendor> findVendorByName(String name);
    boolean existsVendorByName(String name);
    List<Vendor> findVendorByCategory(String category);
}