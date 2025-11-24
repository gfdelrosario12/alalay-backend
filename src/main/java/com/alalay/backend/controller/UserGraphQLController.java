package com.alalay.backend.controller;

import com.alalay.backend.model.User;
import com.alalay.backend.services.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class UserGraphQLController {

    private final UserService userService;

    public UserGraphQLController(UserService userService) {
        this.userService = userService;
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
       MUTATIONS (Input Objects)
       ============================= */

    @MutationMapping
    public User createUser(@Argument CreateUserInput input) {
        LocalDate birthDate = input.birthDate() != null
                ? LocalDate.parse(input.birthDate())
                : null;

        return userService.createUser(
                input.email(),
                input.firstName(),
                input.middleName(),
                input.lastName(),
                input.permanentAddress(),
                input.age(),
                birthDate,
                input.emergencyContact(),
                input.role()
        );
    }

    @MutationMapping
    public User updateUser(@Argument UpdateUserInput input) {
        LocalDate birthDate = input.birthDate() != null
                ? LocalDate.parse(input.birthDate())
                : null;

        return userService.updateUser(
                input.id(),
                input.email(),
                input.firstName(),
                input.middleName(),
                input.lastName(),
                input.permanentAddress(),
                input.age(),
                birthDate,
                input.emergencyContact(),
                input.role()
        );
    }

    @MutationMapping
    public boolean deleteUser(@Argument UUID id) {
        return userService.deleteUser(id);
    }

    /* =============================
       INPUT TYPES (records)
       ============================= */

    public record CreateUserInput(
            String email,
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
            String firstName,
            String middleName,
            String lastName,
            String permanentAddress,
            Integer age,
            String birthDate,
            String emergencyContact,
            User.Role role
    ) {}
}
