package com.alalay.backend.controller;

import com.alalay.backend.model.ResponseLog;
import com.alalay.backend.model.User;
import com.alalay.backend.model.Calamity;
import com.alalay.backend.services.ResponseLogService;
import com.alalay.backend.services.UserService;
import com.alalay.backend.services.CalamityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ResponseLogResolver {

    private final ResponseLogService responseLogService;
    private final UserService userService;
    private final CalamityService calamityService;

    @QueryMapping
    public List<ResponseLog> getResponseLogs() {
        return responseLogService.getAllLogs();
    }

    @QueryMapping
    public List<ResponseLog> getResponseLogsByUser(@Argument String userId) {
        // Use the new getUserById method from UserService
        User user = userService.getUserById(UUID.fromString(userId));
        return responseLogService.getLogsByUser(user);
    }

    @QueryMapping
    public List<ResponseLog> getResponseLogsByCalamity(@Argument String calamityId) {
        Calamity calamity = calamityService.getCalamityById(UUID.fromString(calamityId));
        return responseLogService.getLogsByCalamity(calamity);
    }
}
