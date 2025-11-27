package com.alalay.backend.graphql.inputs;

import com.alalay.backend.model.UserStatusUpdate.Status;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Data
public class UpdateUserStatusInput {
    private UUID userId;
    private UUID calamityId;
    private Status status;
    private String currentSituation;
    private String location;
}
