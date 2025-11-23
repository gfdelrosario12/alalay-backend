package com.alalay.backend.model;

import jakarta.persistence.*;
import lombok.*;

import org.locationtech.jts.geom.Point;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "rescuer_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RescuerStatus {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "rescuer_id", nullable = false)
    private User rescuer;

    private boolean isAvailable;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point lastKnownLocation;

    private java.time.Instant lastActive;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getRescuer() {
        return rescuer;
    }

    public void setRescuer(User rescuer) {
        this.rescuer = rescuer;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Point getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(Point lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public Instant getLastActive() {
        return lastActive;
    }

    public void setLastActive(Instant lastActive) {
        this.lastActive = lastActive;
    }
}
