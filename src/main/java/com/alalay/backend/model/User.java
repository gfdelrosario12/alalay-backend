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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactDetails() {
        return emergencyContactDetails;
    }

    public void setEmergencyContactDetails(String emergencyContactDetails) {
        this.emergencyContactDetails = emergencyContactDetails;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Point currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
