package com.banking.models;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "transactions")
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(name = "txn_date", nullable = false)
    private LocalDate txnDate;

    @Column(name = "txn_time")
    private LocalTime txnTime;

    @Column(length = 255)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDate getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(LocalDate txnDate) {
        this.txnDate = txnDate;
    }

    public LocalTime getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(LocalTime txnTime) {
        this.txnTime = txnTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}


