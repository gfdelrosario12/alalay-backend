package com.alalay.backend.model;

import jakarta.persistence.*;
import lombok.*;

import org.locationtech.jts.geom.Point;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "response_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseLog {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne @JoinColumn(name = "calamity_id", nullable = false)
    private Calamity calamity;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point logLocation;

    private java.time.Instant logDatetime;

    @Column(columnDefinition = "text")
    private String responseDetails;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calamity getCalamity() {
        return calamity;
    }

    public void setCalamity(Calamity calamity) {
        this.calamity = calamity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Point getLogLocation() {
        return logLocation;
    }

    public void setLogLocation(Point logLocation) {
        this.logLocation = logLocation;
    }

    public Instant getLogDatetime() {
        return logDatetime;
    }

    public void setLogDatetime(Instant logDatetime) {
        this.logDatetime = logDatetime;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }
}
