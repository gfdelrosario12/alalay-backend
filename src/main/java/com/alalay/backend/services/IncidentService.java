package com.alalay.backend.services;

import com.alalay.backend.model.Calamity;
import com.alalay.backend.model.Incident;
import com.alalay.backend.model.User;
import com.alalay.backend.repository.CalamityRepository;
import com.alalay.backend.repository.IncidentRepository;
import com.alalay.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final CalamityRepository calamityRepository;
    private final UserRepository userRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Transactional
    public Incident createIncident(
            UUID calamityId,
            UUID rescuerId,       // <-- add this parameter
            Double latitude,
            Double longitude,
            String description,
            String otherAffectedMembers,
            String otherImportantDetails
    ) {
        Optional<Calamity> calamity = calamityRepository.findById(calamityId);
        if (calamity.isEmpty()) throw new RuntimeException("Calamity not found");

        // You need a UserRepository to fetch rescuer
        User rescuer = userRepository.findById(rescuerId)
                .orElseThrow(() -> new RuntimeException("Rescuer not found"));

        Point location = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(longitude, latitude));
        location.setSRID(4326);

        Incident incident = Incident.builder()
                .id(UUID.randomUUID())
                .calamity(calamity.get())
                .detectedDatetime(Instant.now())  // Maps to createdAt in GraphQL
                .description(description)
                .location(location)
                .rescuer(rescuer)                 // <-- assign rescuer here
                .rescueAssigned(false)
                .otherAffectedMembers(otherAffectedMembers)
                .otherImportantDetails(otherImportantDetails)
                .build();

        return incidentRepository.save(incident);
    }


    public List<Incident> getIncidentsByCalamity(UUID calamityId) {
        return incidentRepository.findByCalamityId(calamityId);
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }
}
