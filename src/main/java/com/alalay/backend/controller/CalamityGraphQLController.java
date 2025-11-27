package com.alalay.backend.controller;

import com.alalay.backend.graphql.inputs.CreateCalamityInput;
import com.alalay.backend.model.Calamity;
import com.alalay.backend.services.CalamityService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Controller
public class CalamityGraphQLController {
    private final CalamityService calamityService;

    public CalamityGraphQLController(CalamityService calamityService) {
        this.calamityService = calamityService;
    }

/* =============================
   QUERIES
   ============================= */

    @QueryMapping
    public List<Calamity> calamities() {
        return calamityService.findAll();
    }

    @QueryMapping
    public Calamity calamity(@Argument UUID id) {
        return calamityService.findById(id).orElse(null);
    }


/* =============================
   MUTATIONS
   ============================= */

    @MutationMapping
    public Calamity createCalamity(@Argument("input") CreateCalamityInput input) {
        Instant startInstant = parseInstantSafe(input.startDate(), "startDate");
        Instant endInstant = parseInstantSafe(input.reportedEndDate(), "reportedEndDate");

        return calamityService.createCalamity(
                startInstant,
                input.description(),
                input.calamityCategory(),
                endInstant,
                input.affectedAreasWKT()
        );
    }

    @MutationMapping
    public Calamity updateCalamity(
            @Argument UUID id,
            @Argument String startDate,
            @Argument String description,
            @Argument String calamityCategory,
            @Argument String reportedEndDate,
            @Argument String affectedAreasWKT
    ) {
        Instant startInstant = parseInstantSafe(startDate, "startDate");
        Instant endInstant = parseInstantSafe(reportedEndDate, "reportedEndDate");

        return calamityService.updateCalamity(
                id,
                startInstant,
                description,
                calamityCategory,
                endInstant,
                affectedAreasWKT
        );
    }

    @MutationMapping
    public boolean deleteCalamity(@Argument UUID id) {
        return calamityService.deleteCalamity(id);
    }

    private Instant parseInstantSafe(String value, String fieldName) {
        if (value == null || value.isBlank()) return null;
        try {
            return Instant.parse(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format for " + fieldName + ". Expected ISO-8601 string.", e);
        }
    }

}
