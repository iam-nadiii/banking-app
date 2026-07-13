package com.banking.controller;

import com.banking.model.Vendor;
import com.banking.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
@CrossOrigin(origins = "*")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAll() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getById(@PathVariable Long id) {
        Vendor vendor = vendorService.getById(id);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Vendor> getByName(@PathVariable String name) {
        Vendor vendor = vendorService.getByName(name);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Vendor>> getByCategory(@PathVariable String category) {
        List<Vendor> vendors = vendorService.getByCategory(category);
        return ResponseEntity.ok(vendors);
    }

    @PostMapping
    public ResponseEntity<Vendor> addVendor(@RequestBody Vendor vendor) {
        Vendor created = vendorService.create(vendor);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor) {
        Vendor updated = vendorService.update(id, vendor);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}