package com.banking.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "searches")
public class Search implements Comparable<Search> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;/*Is ID not required? I see it on the schema. I added in case we do, just include getter+setters*/

    // FIX: this was missing entirely — the actual root cause of every
    // saved search being visible (and deletable) by every user. Nothing
    // ever set who a search belonged to.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "search_date")
    private LocalDate searchDate;

    @Column(name = "search_time")
    private LocalTime searchTime;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "vendor_query")
    private String vendor;

    @Column(name = "min_amount")
    private BigDecimal minAmount;

    @Column(name = "max_amount")
    private BigDecimal maxAmount;

    public Search(){
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(LocalDate searchDate) {
        this.searchDate = searchDate;
    }

    public LocalTime getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(LocalTime searchTime) {
        this.searchTime = searchTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public String toString() {
        return "Search{" +
                "searchDate=" + searchDate +
                ", searchTime=" + searchTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                '}';
    }

    @Override
    public int compareTo(Search other) {
        int dateCompare = other.searchDate.compareTo(this.searchDate);

        if (dateCompare != 0) {
            return dateCompare;
        }

        return other.searchTime.compareTo(this.searchTime);
    }


}