package com.banking.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.*;

@Entity
@Table(name = "searches")
public class Search implements Comparable<Search> {

    @Id
    @Column(name = "id")
    private Long Id;/*Is ID not required? I see it on the schema. I added in case we do, just include getter+setters*/

    @Column(name = "searchDate")
    private LocalDate searchDate;

    @Column(name = "searchTime")
    private LocalTime searchTime;

    @Column(name = "startDate")
    private String startDate;

    @Column(name = "endDate")
    private String endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "minAmount")
    private String minAmount;

    @Column(name = "maxAmount")
    private String maxAmount;

//    I had a loaded constructor earlier but saw that it conflicted with the use of an empty one in App, so I just provided an empty one
    public Search(){

    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
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

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public String toString() {
        return "Search{" +
                "searchDate=" + searchDate +
                ", searchTime=" + searchTime +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", minAmount='" + minAmount + '\'' +
                ", maxAmount='" + maxAmount + '\'' +
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
