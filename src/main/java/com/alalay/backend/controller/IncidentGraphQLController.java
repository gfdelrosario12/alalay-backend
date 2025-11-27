package com.alalay.backend.controller;

import com.alalay.backend.model.Incident;
import com.alalay.backend.records.IncidentDTO;
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

    @QueryMapping(name = "getIncidentsByCalamity")
    public List<IncidentDTO> getIncidentsByCalamity(@Argument UUID calamityId) {
        return incidentService.getIncidentsByCalamity(calamityId).stream()
                .map(inc -> new IncidentDTO(
                        inc.getId(),
                        inc.getCalamity() != null ? inc.getCalamity().getId() : null,
                        inc.getRescuer() != null ? inc.getRescuer().getId() : null,
                        inc.getLatitude(),
                        inc.getLongitude(),
                        inc.getDescription(),
                        inc.getOtherAffectedMembers(),
                        inc.getOtherImportantDetails(),
                        inc.getDetectedDatetime() != null ? inc.getDetectedDatetime().toString() : null
                ))
                .toList();
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
