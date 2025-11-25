package com.alalay.backend.records;

import com.alalay.backend.model.User;

import java.util.UUID;

public record AuthPayload(
        String token,
        User user,
        UUID id,
        String email,
        String firstName,
        String lastName,
        User.Role role
) {}
