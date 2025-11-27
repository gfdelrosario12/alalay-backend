package com.alalay.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Geometry;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "calamities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calamity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private Instant startDate;

    @Column(columnDefinition = "text")
    private String description;

    private String calamityCategory;

    private Instant reportedEndDate;

    // store as WKT string OR JTS geometry. Hibernate-spatial mapping if configured:
    @Column(columnDefinition = "geometry")
    private Geometry affectedAreas;

    private Instant createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.STARTED;  // <-- default value for existing rows


    public enum Status {
        STARTED,
        DONE
    }

    // =========================
    // Getters & Setters
    // =========================

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
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

    public Instant getReportedEndDate() {
        return reportedEndDate;
    }

    public void setReportedEndDate(Instant reportedEndDate) {
        this.reportedEndDate = reportedEndDate;
    }

    public Geometry getAffectedAreas() {
        return affectedAreas;
    }

    public void setAffectedAreas(Geometry affectedAreas) {
        this.affectedAreas = affectedAreas;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Add this getter for GraphQL
    public String getAffectedAreasWKT() {
        return affectedAreas != null ? affectedAreas.toText() : null;
    }

}
