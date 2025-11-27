package com.alalay.backend.controller;

import com.alalay.backend.model.Incident;
import com.alalay.backend.services.IncidentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class IncidentGraphQLController {

    private final IncidentService incidentService;

    // GraphQL Mutation to create an incident
    @MutationMapping
    public Incident createIncident(@Argument CreateIncidentInput input) {
        return incidentService.createIncident(
                input.getCalamityId(),
                input.getRescuerId(),
                input.getLatitude(),
                input.getLongitude(),
                input.getDescription(),
                input.getOtherAffectedMembers(),
                input.getOtherImportantDetails()
        );
    }

    // GraphQL Query to get all incidents
    @QueryMapping(name = "getIncidents")
    public List<Incident> getIncidents() {
        return incidentService.getAllIncidents();
    }

    // GraphQL Query to get incidents by calamity ID
    @QueryMapping(name = "getIncidentsByCalamity")
    public List<Incident> getIncidentsByCalamity(@Argument UUID calamityId) {
        return incidentService.getIncidentsByCalamity(calamityId);
    }

    // Input DTO for GraphQL
    @Data
    public static class CreateIncidentInput {
        private UUID calamityId;
        private UUID rescuerId;
        private Double latitude;
        private Double longitude;
        private String description;
        private String otherAffectedMembers;
        private String otherImportantDetails;
    }
}
