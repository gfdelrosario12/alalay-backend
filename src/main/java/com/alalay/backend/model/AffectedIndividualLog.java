package com.alalay.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "affected_individuals_log")
public class AffectedIndividualLog {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "calamity_id", nullable = false)
    private Calamity calamity;

    @Column(nullable = false)
    private LocalDateTime instanceDatetime;

    private String description;
    private String instanceLocation;
    private Boolean isResponded = false;
    private LocalDateTime responseDatetime;

    @ManyToOne
    @JoinColumn(name = "rescuer_id")
    private User rescuer;

    private String otherAffectedMembers;
    private String otherImportantDetails;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Calamity getCalamity() {
        return calamity;
    }

    public void setCalamity(Calamity calamity) {
        this.calamity = calamity;
    }

    public LocalDateTime getInstanceDatetime() {
        return instanceDatetime;
    }

    public void setInstanceDatetime(LocalDateTime instanceDatetime) {
        this.instanceDatetime = instanceDatetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstanceLocation() {
        return instanceLocation;
    }

    public void setInstanceLocation(String instanceLocation) {
        this.instanceLocation = instanceLocation;
    }

    public Boolean getResponded() {
        return isResponded;
    }

    public void setResponded(Boolean responded) {
        isResponded = responded;
    }

    public LocalDateTime getResponseDatetime() {
        return responseDatetime;
    }

    public void setResponseDatetime(LocalDateTime responseDatetime) {
        this.responseDatetime = responseDatetime;
    }

    public User getRescuer() {
        return rescuer;
    }

    public void setRescuer(User rescuer) {
        this.rescuer = rescuer;
    }

    public String getOtherAffectedMembers() {
        return otherAffectedMembers;
    }

    public void setOtherAffectedMembers(String otherAffectedMembers) {
        this.otherAffectedMembers = otherAffectedMembers;
    }

    public String getOtherImportantDetails() {
        return otherImportantDetails;
    }

    public void setOtherImportantDetails(String otherImportantDetails) {
        this.otherImportantDetails = otherImportantDetails;
    }
}