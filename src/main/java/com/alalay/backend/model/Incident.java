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

    // Renamed field for proper JPA query derivation
    private boolean rescueAssigned;

    @Column(columnDefinition = "text")
    private String otherAffectedMembers;

    @Column(columnDefinition = "text")
    private String otherImportantDetails;

}
