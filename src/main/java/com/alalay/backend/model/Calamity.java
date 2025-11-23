package com.alalay.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private java.time.Instant startDate;

    @Column(columnDefinition = "text")
    private String description;

    private String calamityCategory;
    private java.time.Instant reportedEndDate;

    // store as WKT string OR JTS geometry. Hibernate-spatial mapping if configured:
    @Column(columnDefinition = "geometry")
    private org.locationtech.jts.geom.Geometry affectedAreas;

    private java.time.Instant createdAt;

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
}
