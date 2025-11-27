    package com.alalay.backend.model;

    import jakarta.persistence.*;
    import lombok.*;
    import org.locationtech.jts.geom.Point;

    import java.time.Instant;
    import java.time.LocalDate;
    import java.util.UUID;

    @Entity
    @Table(name = "users")
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
        private java.time.LocalDate birthDate;
        private String emergencyContact;

        // Add these fields to your User entity

        @Column(name = "phone_number")
        private String phoneNumber;

        @Column(name = "current_location", columnDefinition = "geometry(Point,4326)")
        private Point currentLocation;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Role role;

        private Instant createdAt;

        public enum Role { ADMIN, RESCUER, RESIDENT }

        // Add getters and setters
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

        // Add these to your GraphQL resolver if you need to expose lat/lon separately
        public Double getCurrentLatitude() {
            return currentLocation != null ? currentLocation.getY() : null;
        }

        public Double getCurrentLongitude() {
            return currentLocation != null ? currentLocation.getX() : null;
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

        public String getEmergencyContact() {
            return emergencyContact;
        }

        public void setEmergencyContact(String emergencyContact) {
            this.emergencyContact = emergencyContact;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Instant getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
        }
    }