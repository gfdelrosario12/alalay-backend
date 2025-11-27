package com.alalay.backend.services;

import com.alalay.backend.model.User;
import com.alalay.backend.model.Calamity;
import com.alalay.backend.model.UserStatusUpdate;
import com.alalay.backend.model.UserStatusUpdate.Status;
import com.alalay.backend.repository.UserRepository;
import com.alalay.backend.repository.CalamityRepository;
import com.alalay.backend.repository.UserStatusUpdateRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserStatusUpdateService {

    private final UserStatusUpdateRepository statusRepo;
    private final UserRepository userRepo;
    private final CalamityRepository calamityRepo;

    public UserStatusUpdateService(UserStatusUpdateRepository statusRepo,
                                   UserRepository userRepo,
                                   CalamityRepository calamityRepo) {
        this.statusRepo = statusRepo;
        this.userRepo = userRepo;
        this.calamityRepo = calamityRepo;
    }

    public UserStatusUpdate updateStatus(UUID userId, UUID calamityId, Status status,
                                         String currentSituation, Point location) {
        Optional<User> userOpt = userRepo.findById(userId);
        Optional<Calamity> calamityOpt = calamityRepo.findById(calamityId);

        if (userOpt.isEmpty() || calamityOpt.isEmpty()) {
            throw new RuntimeException("User or Calamity not found");
        }

        UserStatusUpdate statusUpdate = new UserStatusUpdate();
        statusUpdate.setId(UUID.randomUUID());
        statusUpdate.setUser(userOpt.get());
        statusUpdate.setCalamity(calamityOpt.get());
        statusUpdate.setStatus(status);
        statusUpdate.setCurrentSituation(currentSituation);
        statusUpdate.setLocation(location);
        statusUpdate.setUpdateDatetime(Instant.now());

        return statusRepo.save(statusUpdate);
    }

}
