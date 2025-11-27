package com.alalay.backend.services;

import com.alalay.backend.model.User;
import com.alalay.backend.model.RescuerStatus;
import com.alalay.backend.repository.UserRepository;
import com.alalay.backend.repository.RescuerStatusRepository;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final RescuerStatusRepository rescuerStatusRepo;
    private final PasswordEncoder passwordEncoder;
    private final GeometryFactory geometryFactory;

    public UserService(UserRepository userRepo, RescuerStatusRepository rescuerStatusRepo,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.rescuerStatusRepo = rescuerStatusRepo;
        this.passwordEncoder = passwordEncoder;
        this.geometryFactory = new GeometryFactory();
    }

    /* =============================
       BASIC FINDERS
       ============================= */
    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepo.findById(id);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    /* =============================
       CREATE USER
       ============================= */
    @Transactional
    public User createUser(
            String email,
            String rawPassword,
            String firstName,
            String middleName,
            String lastName,
            String permanentAddress,
            Integer age,
            LocalDate birthDate,
            String emergencyContact,
            String phoneNumber,
            User.Role role
    ) {
        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(email)
                .password(hashedPassword)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .permanentAddress(permanentAddress)
                .age(age)
                .birthDate(birthDate)
                .emergencyContact(emergencyContact)
                .phoneNumber(phoneNumber)
                .currentLocation(null)
                .role(role)
                .createdAt(Instant.now())
                .build();

        User saved = userRepo.save(user);

        if (role == User.Role.RESCUER) {
            RescuerStatus status = RescuerStatus.builder()
                    .id(UUID.randomUUID())
                    .rescuer(saved)
                    .isAvailable(true)
                    .lastKnownLocation(null)
                    .build();
            rescuerStatusRepo.save(status);
        }

        return saved;
    }

    /* =============================
       UPDATE USER
       ============================= */
    @Transactional
    public User updateUser(
            UUID id,
            String email,
            String rawPassword,
            String firstName,
            String middleName,
            String lastName,
            String permanentAddress,
            Integer age,
            LocalDate birthDate,
            String emergencyContact,
            String phoneNumber,
            User.Role role
    ) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (email != null) user.setEmail(email);
        if (rawPassword != null) user.setPassword(passwordEncoder.encode(rawPassword));
        if (firstName != null) user.setFirstName(firstName);
        if (middleName != null) user.setMiddleName(middleName);
        if (lastName != null) user.setLastName(lastName);
        if (permanentAddress != null) user.setPermanentAddress(permanentAddress);
        if (age != null) user.setAge(age);
        if (birthDate != null) user.setBirthDate(birthDate);
        if (emergencyContact != null) user.setEmergencyContact(emergencyContact);
        if (phoneNumber != null) user.setPhoneNumber(phoneNumber);
        if (role != null) user.setRole(role);

        return userRepo.save(user);
    }

    /* =============================
       DELETE USER
       ============================= */
    @Transactional
    public boolean deleteUser(UUID id) {
        if (!userRepo.existsById(id)) return false;
        rescuerStatusRepo.deleteByRescuerId(id);
        userRepo.deleteById(id);
        return true;
    }

    /* =============================
       LOCATION & PHONE NUMBER UPDATES
       ============================= */
    @Transactional
    public void updateUserLocation(UUID userId, double latitude, double longitude) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        user.setCurrentLocation(location);
        userRepo.save(user);
    }

    @Transactional
    public void updateUserPhoneNumber(UUID userId, String phoneNumber) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPhoneNumber(phoneNumber);
        userRepo.save(user);
    }

    public Point getUserLocation(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getCurrentLocation();
    }

    /* =============================
       RESCUER STATUS
       ============================= */
    @Transactional
    public void setRescuerAvailability(UUID rescuerId, boolean available) {
        RescuerStatus status = rescuerStatusRepo.findByRescuerId(rescuerId)
                .orElseThrow(() -> new RuntimeException("Rescuer status not found"));
        status.setAvailable(available);
        rescuerStatusRepo.save(status);
    }

    /* =============================
       VERIFY PASSWORD
       ============================= */
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

}