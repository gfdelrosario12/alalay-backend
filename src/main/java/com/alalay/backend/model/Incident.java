package com.alalay.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "incidents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "calamity_id", nullable = false)
    private Calamity calamity;

    @ManyToOne
    @JoinColumn(name = "rescuer_id")
    private User rescuer;

    private Instant detectedDatetime;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    private boolean rescueAssigned;

    @Column(columnDefinition = "text")
    private String otherAffectedMembers;

    @Column(columnDefinition = "text")
    private String otherImportantDetails;

    // --------------------------
    // GraphQL convenience getters
    // --------------------------

    /**
     * Expose calamityId for GraphQL queries
     */
    public UUID getCalamityId() {
        return calamity != null ? calamity.getId() : null;
    }

    /**
     * Expose rescuerId for GraphQL queries
     */
    public UUID getRescuerId() {
        return rescuer != null ? rescuer.getId() : null;
    }

    /**
     * Map detectedDatetime to a GraphQL-friendly ISO string
     */
    public String getCreatedDatetime() {
        return detectedDatetime != null ? detectedDatetime.toString() : null;
    }

    /**
     * Optional: convenience getters for latitude/longitude in GraphQL
     */
    public Double getLatitude() {
        return location != null ? location.getY() : null;
    }

    public Double getLongitude() {
        return location != null ? location.getX() : null;
    }
}
