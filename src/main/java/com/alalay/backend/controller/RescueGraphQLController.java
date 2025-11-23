package com.alalay.backend.controller;

import com.alalay.backend.model.RescueAssignInput;
import com.alalay.backend.model.RescueTask;
import com.alalay.backend.services.RescueService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Controller
public class RescueGraphQLController {
    private final RescueService rescueService;

    public RescueGraphQLController(RescueService rescueService) {
        this.rescueService = rescueService;
    }

    @MutationMapping
    public RescueTask createRescueTask(@Argument RescueAssignInput input) {
        return rescueService.assignRescue(input.getIncidentId(), input.getRescuerId(), input.getNotes());
    }

    @QueryMapping
    public List<RescueTask> rescueTasks(@Argument RescueTask.Status status) {
        return rescueService.findByStatus(status);
    }

    @MutationMapping
    public RescueTask updateRescueTaskStatus(@Argument UUID id, @Argument RescueTask.Status status, @Argument Instant completionDatetime) {
        return rescueService.updateStatus(id, status, completionDatetime);
    }
}
