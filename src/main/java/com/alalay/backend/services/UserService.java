package com.alalay.backend.services;

import com.alalay.backend.model.User;
import com.alalay.backend.model.RescuerStatus;
import com.alalay.backend.repository.UserRepository;
import com.alalay.backend.repository.RescuerStatusRepository;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
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

    public UserService(UserRepository userRepo, RescuerStatusRepository rescuerStatusRepo) {
        this.userRepo = userRepo;
        this.rescuerStatusRepo = rescuerStatusRepo;
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

    public List<User> findNearestAvailableRescuers(double lat, double lon, int limit) {
        return userRepo.findNearestAvailableRescuers(lat, lon, limit);
    }


    /* =============================
       CREATE USER
       ============================= */

    @Transactional
    public User createUser(
            String email,
            String firstName,
            String middleName,
            String lastName,
            String permanentAddress,
            Integer age,
            LocalDate birthDate,
            String emergencyContact,
            User.Role role
    ) {

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(email)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .permanentAddress(permanentAddress)
                .age(age)
                .birthDate(birthDate)
                .emergencyContact(emergencyContact)
                .role(role)
                .createdAt(Instant.now())
                .build();

        User saved = userRepo.save(user);

        // If this user is a Rescuer → auto-create status record
        if (role == User.Role.Rescuer) {
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
            String firstName,
            String middleName,
            String lastName,
            String permanentAddress,
            Integer age,
            LocalDate birthDate,
            String emergencyContact,
            User.Role role
    ) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (email != null) user.setEmail(email);
        if (firstName != null) user.setFirstName(firstName);
        if (middleName != null) user.setMiddleName(middleName);
        if (lastName != null) user.setLastName(lastName);
        if (permanentAddress != null) user.setPermanentAddress(permanentAddress);
        if (age != null) user.setAge(age);
        if (birthDate != null) user.setBirthDate(birthDate);
        if (emergencyContact != null) user.setEmergencyContact(emergencyContact);
        if (role != null) user.setRole(role);

        return userRepo.save(user);
    }


    /* =============================
       DELETE USER
       ============================= */

    @Transactional
    public boolean deleteUser(UUID id) {

        if (!userRepo.existsById(id)) return false;

        // Delete rescuer status if needed
        rescuerStatusRepo.deleteByRescuerId(id);

        userRepo.deleteById(id);
        return true;
    }


    /* =============================
       RESCUER STATUS UPDATES
       ============================= */

    @Transactional
    public void setRescuerAvailability(UUID rescuerId, boolean available) {
        RescuerStatus status = rescuerStatusRepo.findByRescuerId(rescuerId)
                .orElseThrow(() -> new RuntimeException("Rescuer status not found"));

        status.setAvailable(available);
        rescuerStatusRepo.save(status);
    }

    @Transactional
    public void updateRescuerLocation(UUID rescuerId, double latitude, double longitude) {
        RescuerStatus status = rescuerStatusRepo.findByRescuerId(rescuerId)
                .orElseThrow(() -> new RuntimeException("Rescuer status not found"));

        GeometryFactory gf = new GeometryFactory();
        status.setLastKnownLocation(
                gf.createPoint(new Coordinate(longitude, latitude))
        );

        rescuerStatusRepo.save(status);
    }
}
