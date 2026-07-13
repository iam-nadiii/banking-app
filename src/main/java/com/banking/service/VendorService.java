package com.banking.service;

import com.banking.exception.DatabaseOperationException;
import com.banking.exception.DuplicateResourceException;
import com.banking.exception.InvalidInputException;
import com.banking.exception.ResourceNotFoundException;
import com.banking.model.Vendor;
import com.banking.repository.VendorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getById(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidInputException("Vendor ID must be a positive number");
        }
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with this id: " + id));
    }

    public Vendor getByName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("Vendor name cannot be empty");
        }
        return vendorRepository.findVendorByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with this name: " + name));
    }

    public List<Vendor> getByCategory(String category) {
        if (category == null || category.isBlank()) {
            throw new InvalidInputException("Vendor category cannot be empty");
        }
        return vendorRepository.findVendorByCategory(category);
    }

    @Transactional
    public Vendor create(Vendor vendor) {
        if (vendor.getName() == null || vendor.getName().isBlank()) {
            throw new InvalidInputException("Vendor name cannot be empty");
        }
        if (vendorRepository.existsVendorByName(vendor.getName())) {
            throw new DuplicateResourceException("A vendor with this name already exists: " + vendor.getName());
        }
        try {
            return vendorRepository.save(vendor);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to save vendor", e);
        }
    }

    public void delete(Long id) {
        getById(id);
        vendorRepository.deleteById(id);
    }

    public Vendor update(Long id, Vendor vendor) {
        if (vendor.getName() == null || vendor.getName().isBlank()) {
            throw new InvalidInputException("Vendor name cannot be empty");
        }
        Vendor existing = getById(id);

        if (!existing.getName().equalsIgnoreCase(vendor.getName())
                && vendorRepository.existsVendorByName(vendor.getName())) {
            throw new DuplicateResourceException("A vendor with this name already exists: " + vendor.getName());
        }
        existing.setName(vendor.getName());
        existing.setCategory(vendor.getCategory());

        try {
            return vendorRepository.save(existing);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update vendor", e);
        }
    }
}