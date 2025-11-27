package com.alalay.backend.controller;

import com.alalay.backend.graphql.inputs.UpdateUserStatusInput;
import com.alalay.backend.model.UserStatusUpdate;
import com.alalay.backend.services.UserStatusUpdateService;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserStatusUpdateController {

    private final UserStatusUpdateService statusService;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public UserStatusUpdateController(UserStatusUpdateService statusService) {
        this.statusService = statusService;
    }

    @MutationMapping
    public UserStatusUpdate updateUserStatus(@Argument UpdateUserStatusInput input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        if (input.getUserId() == null || input.getCalamityId() == null) {
            throw new IllegalArgumentException("User ID and Calamity ID are required");
        }
        if (input.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        String currentSituation = input.getCurrentSituation() != null ? input.getCurrentSituation() : "";

        Point location = null;
        if (input.getLocation() != null && !input.getLocation().isBlank()) {
            String[] parts = input.getLocation().split(",");
            if (parts.length == 2) {
                try {
                    double lat = Double.parseDouble(parts[0].trim());
                    double lng = Double.parseDouble(parts[1].trim());
                    location = geometryFactory.createPoint(new Coordinate(lng, lat));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid location format. Expected 'lat,lng'.", e);
                }
            } else {
                throw new IllegalArgumentException("Invalid location format. Expected 'lat,lng'.");
            }
        }

        return statusService.updateStatus(
                input.getUserId(),
                input.getCalamityId(),
                input.getStatus(),
                currentSituation,
                location
        );
    }

}
