package com.slmas.Sl.domain;

import java.time.LocalDate;
import java.util.List;

public class Statistics {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<CountEntry> itemsByRecordType;
    private List<CountEntry> itemsByArea;
    private List<CountEntry> claimsByProblemType;
    private List<CountEntry> itemsByUser;
    private List<CountEntry> claimsByClaimant;
    private Integer totalItems;
    private Integer totalClaims;
    private Integer totalCompletedWorks;
    private Integer totalRecurringTasks;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<CountEntry> getItemsByRecordType() {
        return itemsByRecordType;
    }

    public void setItemsByRecordType(List<CountEntry> itemsByRecordType) {
        this.itemsByRecordType = itemsByRecordType;
    }

    public List<CountEntry> getItemsByArea() {
        return itemsByArea;
    }

    public void setItemsByArea(List<CountEntry> itemsByArea) {
        this.itemsByArea = itemsByArea;
    }

    public List<CountEntry> getClaimsByProblemType() {
        return claimsByProblemType;
    }

    public void setClaimsByProblemType(List<CountEntry> claimsByProblemType) {
        this.claimsByProblemType = claimsByProblemType;
    }

    public List<CountEntry> getItemsByUser() {
        return itemsByUser;
    }

    public void setItemsByUser(List<CountEntry> itemsByUser) {
        this.itemsByUser = itemsByUser;
    }

    public List<CountEntry> getClaimsByClaimant() {
        return claimsByClaimant;
    }

    public void setClaimsByClaimant(List<CountEntry> claimsByClaimant) {
        this.claimsByClaimant = claimsByClaimant;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalClaims() {
        return totalClaims;
    }

    public void setTotalClaims(Integer totalClaims) {
        this.totalClaims = totalClaims;
    }

    public Integer getTotalCompletedWorks() {
        return totalCompletedWorks;
    }

    public void setTotalCompletedWorks(Integer totalCompletedWorks) {
        this.totalCompletedWorks = totalCompletedWorks;
    }

    public Integer getTotalRecurringTasks() {
        return totalRecurringTasks;
    }

    public void setTotalRecurringTasks(Integer totalRecurringTasks) {
        this.totalRecurringTasks = totalRecurringTasks;
    }
}
