package com.alalay.backend.controller;

import com.alalay.backend.model.Incident;
import com.alalay.backend.services.IncidentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    // Create Incident
    @PostMapping
    public Incident createIncident(@RequestBody CreateIncidentRequest req) {
        return incidentService.createIncident(
                req.calamityId,
                req.latitude,
                req.longitude,
                req.description,
                req.otherAffectedMembers,
                req.otherImportantDetails
        );
    }

    // Get all incidents
    @GetMapping
    public List<Incident> getAll() {
        return incidentService.getAllIncidents();
    }

    // Get incidents by calamity
    @GetMapping("/calamity/{id}")
    public List<Incident> getByCalamity(@PathVariable UUID id) {
        return incidentService.getIncidentsByCalamity(id);
    }

    // DTO for creating an incident
    @Data
    public static class CreateIncidentRequest {
        public UUID calamityId;
        public Double latitude;
        public Double longitude;
        public String description;
        public String otherAffectedMembers;
        public String otherImportantDetails;
    }
}
