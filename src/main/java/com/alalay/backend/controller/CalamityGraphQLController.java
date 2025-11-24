package com.alalay.backend.controller;

import com.alalay.backend.model.Calamity;
import com.alalay.backend.services.CalamityService;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
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
    public Calamity createCalamity(
            @Argument String startDate,
            @Argument String description,
            @Argument String calamityCategory,
            @Argument String reportedEndDate,
            @Argument String affectedAreasWKT
    ) {
        return calamityService.createCalamity(
                Instant.parse(startDate),
                description,
                calamityCategory,
                reportedEndDate != null ? Instant.parse(reportedEndDate) : null,
                affectedAreasWKT
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
        return calamityService.updateCalamity(
                id,
                startDate != null ? Instant.parse(startDate) : null,
                description,
                calamityCategory,
                reportedEndDate != null ? Instant.parse(reportedEndDate) : null,
                affectedAreasWKT
        );
    }

    @MutationMapping
    public boolean deleteCalamity(@Argument UUID id) {
        return calamityService.deleteCalamity(id);
    }
}
