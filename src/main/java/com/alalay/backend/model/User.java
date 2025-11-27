package com.alalay.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String middleName;
    private String lastName;
    private String permanentAddress;
    private Integer age;
    private LocalDate birthDate;

    // Separate emergency contact fields
    private String emergencyContactName;
    private String emergencyContactDetails;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "current_location", columnDefinition = "geometry(Point,4326)")
    private Point currentLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Instant createdAt;

    public enum Role { ADMIN, RESCUER, RESIDENT }

    // --------------------------
    // GraphQL Helper Methods
    // --------------------------

    /**
     * Latitude for GraphQL
     */
    public Double getCurrentLatitude() {
        return currentLocation != null ? currentLocation.getY() : null;
    }

    /**
     * Longitude for GraphQL
     */
    public Double getCurrentLongitude() {
        return currentLocation != null ? currentLocation.getX() : null;
    }

    /**
     * Convert Instant to ISO String for GraphQL
     */
    public String getCreatedAt() {
        return createdAt != null ? createdAt.toString() : null;
    }

    /**
     * Full Name for convenience
     */
    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (firstName != null) fullName.append(firstName).append(" ");
        if (middleName != null) fullName.append(middleName).append(" ");
        if (lastName != null) fullName.append(lastName);
        return fullName.toString().trim();
    }
}
