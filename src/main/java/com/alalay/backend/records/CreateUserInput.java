package com.alalay.backend.records;

import com.alalay.backend.model.User;

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
            String phoneNumber,
            User.Role role
    ) {}