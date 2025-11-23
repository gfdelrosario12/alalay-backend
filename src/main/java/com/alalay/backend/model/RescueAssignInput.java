package com.alalay.backend.model;

import java.util.UUID;

public class RescueAssignInput {
    private UUID incidentId;
    private UUID rescuerId;
    private String notes;

    public UUID getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(UUID incidentId) {
        this.incidentId = incidentId;
    }

    public UUID getRescuerId() {
        return rescuerId;
    }

    public void setRescuerId(UUID rescuerId) {
        this.rescuerId = rescuerId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
