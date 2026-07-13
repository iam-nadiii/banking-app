package com.banking.model;

import jakarta.persistence.*;

@Entity
@Table (name = "vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
