package com.alalay.backend.controller;

import com.alalay.backend.model.User;
import com.alalay.backend.services.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Controller
public class UserGraphQLController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserGraphQLController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /* =============================
       QUERIES
       ============================= */
    @QueryMapping
    public List<User> users() {
        return userService.findAll();
    }

    @QueryMapping
    public User user(@Argument UUID id) {
        return userService.findById(id).orElse(null);
    }

    /* =============================
       MUTATIONS
       ============================= */
    @MutationMapping
    public User createUser(@Argument CreateUserInput input) {
        try {
            LocalDate birthDate = parseDateSafe(input.birthDate());
            if (input.role() == null) {
                throw new IllegalArgumentException("Role is required");
            }

            String hashedPassword = passwordEncoder.encode(input.password());

            return userService.createUser(
                    input.email(),
                    hashedPassword,
                    input.firstName(),
                    input.middleName(),
                    input.lastName(),
                    input.permanentAddress(),
                    input.age(),
                    birthDate,
                    input.emergencyContact(),
                    input.role()
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create user: " + e.getMessage());
        }
    }

    @MutationMapping
    public User updateUser(@Argument UpdateUserInput input) {
        requireAuthentication();
        try {
            LocalDate birthDate = parseDateSafe(input.birthDate());
            String hashedPassword = input.password() != null ? passwordEncoder.encode(input.password()) : null;

            return userService.updateUser(
                    input.id(),
                    input.email(),
                    hashedPassword,
                    input.firstName(),
                    input.middleName(),
                    input.lastName(),
                    input.permanentAddress(),
                    input.age(),
                    birthDate,
                    input.emergencyContact(),
                    input.role()
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update user: " + e.getMessage());
        }
    }

    @MutationMapping
    public boolean deleteUser(@Argument UUID id) {
        requireAuthentication();
        try {
            return userService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete user: " + e.getMessage());
        }
    }

    /* =============================
       HELPER
       ============================= */
    private void requireAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthorized: JWT required");
        }
    }

    private LocalDate parseDateSafe(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format, expected yyyy-MM-dd");
        }
    }

    /* =============================
       INPUT TYPES
       ============================= */
    public record CreateUserInput(
            String email,
            String password,
            String firstName,
            String middleName,
            String lastName,
            String permanentAddress,
            Integer age,
            String birthDate,
            String emergencyContact,
            User.Role role
    ) {}

    public record UpdateUserInput(
            UUID id,
            String email,
            String password,
            String firstName,
            String middleName,
            String lastName,
            String permanentAddress,
            Integer age,
            String birthDate,
            String emergencyContact,
            User.Role role
    ){}
}
