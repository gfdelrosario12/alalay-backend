package com.alalay.backend.services;

import com.alalay.backend.model.User;
import com.alalay.backend.model.RescuerStatus;
import com.alalay.backend.repository.UserRepository;
import com.alalay.backend.repository.RescuerStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final RescuerStatusRepository rescuerStatusRepo;

    public UserService(UserRepository userRepo, RescuerStatusRepository rescuerStatusRepo) {
        this.userRepo = userRepo;
        this.rescuerStatusRepo = rescuerStatusRepo;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public java.util.Optional<User> findById(UUID id) {
        return userRepo.findById(id);
    }

    /**
     * Find nearest available rescuers based on lat/lon and optional limit.
     */
    public List<User> findNearestAvailableRescuers(double lat, double lon, int limit) {
        return userRepo.findNearestAvailableRescuers(lat, lon, limit);
    }

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
        org.locationtech.jts.geom.GeometryFactory gf = new org.locationtech.jts.geom.GeometryFactory();
        status.setLastKnownLocation(gf.createPoint(new org.locationtech.jts.geom.Coordinate(longitude, latitude)));
        rescuerStatusRepo.save(status);
    }
}
