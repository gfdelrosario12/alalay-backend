package com.alalay.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "calamities")
public class Calamity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private LocalDateTime startDate;
    private String description;
    private String calamityCategory;
    private LocalDateTime reportedEndDate;
    private String affectedAreas;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalamityCategory() {
        return calamityCategory;
    }

    public void setCalamityCategory(String calamityCategory) {
        this.calamityCategory = calamityCategory;
    }

    public LocalDateTime getReportedEndDate() {
        return reportedEndDate;
    }

    public void setReportedEndDate(LocalDateTime reportedEndDate) {
        this.reportedEndDate = reportedEndDate;
    }

    public String getAffectedAreas() {
        return affectedAreas;
    }

    public void setAffectedAreas(String affectedAreas) {
        this.affectedAreas = affectedAreas;
    }
}