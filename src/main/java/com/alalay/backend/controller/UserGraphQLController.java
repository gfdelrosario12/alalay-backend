package com.alalay.backend.controller;

import com.alalay.backend.graphql.inputs.LoginInput;
import com.alalay.backend.config.JwtService;
import com.alalay.backend.model.User;
import com.alalay.backend.records.AuthPayload;
import com.alalay.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Controller
public class UserGraphQLController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    public UserGraphQLController(UserService userService,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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

            return userService.createUser(
                    input.email(),
                    input.password(),
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
            throw new RuntimeException("Failed to create user: " + e.getMessage(), e);
        }
    }

    @MutationMapping
    public User updateUser(@Argument UpdateUserInput input) {
        requireAuthentication();
        try {
            LocalDate birthDate = parseDateSafe(input.birthDate());
            return userService.updateUser(
                    input.id(),
                    input.email(),
                    input.password(),
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
            throw new RuntimeException("Failed to update user: " + e.getMessage(), e);
        }
    }

    @MutationMapping
    public boolean deleteUser(@Argument UUID id) {
        requireAuthentication();
        try {
            return userService.deleteUser(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }

    /* =============================
       LOGIN (returns token + user)
       ============================= */
    @MutationMapping
    public AuthPayload login(@Argument LoginInput input) {
        User user = userService.findByEmail(input.getEmail());
        if (user == null || !userService.verifyPassword(input.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        response.addHeader("Set-Cookie", String.format("token=%s; HttpOnly; Path=/; SameSite=Lax", token));

        return new AuthPayload(
                token,
                user,
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
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
            throw new IllegalArgumentException("Invalid date format, expected yyyy-MM-dd", e);
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
    ) {}

    /* =============================
       LOGIN RESPONSE
       ============================= */
    public record LoginResponse(String token, User user) {}
}
