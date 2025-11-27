package com.alalay.backend.graphql.inputs;

import com.alalay.backend.model.Calamity.Status;

public record UpdateCalamityInput(
        String id,
        String startDate,
        String description,
        String calamityCategory,
        String reportedEndDate,
        String affectedAreasWKT,
        Status status
) {}