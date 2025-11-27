package com.alalay.backend.graphql.inputs;

public record CreateCalamityInput(
        String startDate,
        String description,
        String calamityCategory,
        String reportedEndDate,
        String affectedAreasWKT
) {}
