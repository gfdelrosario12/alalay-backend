package com.alalay.backend.controller;

import com.alalay.backend.model.User;
import com.alalay.backend.services.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class UserGraphQLController {

    private final UserService userService;

    public UserGraphQLController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public List<User> users() {
        return userService.findAll();
    }

    @QueryMapping
    public User user(@Argument UUID id) {
        return userService.findById(id).orElse(null);
    }

    @QueryMapping
    public List<User> availableRescuers(
            @Argument double lat,
            @Argument double lon,
            @Argument Integer limit
    ) {
        return userService.findNearestAvailableRescuers(
                lat,
                lon,
                limit == null ? 10 : limit
        );
    }
}
