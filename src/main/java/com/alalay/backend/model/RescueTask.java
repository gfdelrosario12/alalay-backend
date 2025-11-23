package com.alalay.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "rescue_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RescueTask {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "incident_id", nullable = false)
    private Incident incident;

    @ManyToOne @JoinColumn(name = "assigned_rescuer_id", nullable = false)
    private User assignedRescuer;

    private java.time.Instant assignedDatetime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private java.time.Instant completionDatetime;

    @Column(columnDefinition = "text")
    private String notes;

    public enum Status { Pending, InProgress, Completed, Failed }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public User getAssignedRescuer() {
        return assignedRescuer;
    }

    public void setAssignedRescuer(User assignedRescuer) {
        this.assignedRescuer = assignedRescuer;
    }

    public Instant getAssignedDatetime() {
        return assignedDatetime;
    }

    public void setAssignedDatetime(Instant assignedDatetime) {
        this.assignedDatetime = assignedDatetime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getCompletionDatetime() {
        return completionDatetime;
    }

    public void setCompletionDatetime(Instant completionDatetime) {
        this.completionDatetime = completionDatetime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
