package com.alalay.backend.services;

import com.alalay.backend.model.Incident;
import com.alalay.backend.model.RescueTask;
import com.alalay.backend.model.User;
import com.alalay.backend.repository.IncidentRepository;
import com.alalay.backend.repository.RescueTaskRepository;
import com.alalay.backend.repository.RescuerStatusRepository;
import com.alalay.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class RescueService {
    private final RescueTaskRepository rescueRepo;
    private final RescuerStatusRepository rescuerStatusRepo;
    private final IncidentRepository incidentRepo;
    private final UserRepository userRepo;

    public RescueService(RescueTaskRepository rescueRepo, RescuerStatusRepository rescuerStatusRepo,
                         IncidentRepository incidentRepo, UserRepository userRepo) {
        this.rescueRepo = rescueRepo;
        this.rescuerStatusRepo = rescuerStatusRepo;
        this.incidentRepo = incidentRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public RescueTask assignRescue(UUID incidentId, UUID rescuerId, String notes) {
        Incident incident = incidentRepo.findById(incidentId).orElseThrow();
        User rescuer = userRepo.findById(rescuerId).orElseThrow();

        // create task
        RescueTask task = RescueTask.builder()
                .id(UUID.randomUUID())
                .incident(incident)
                .assignedRescuer(rescuer)
                .assignedDatetime(Instant.now())
                .status(RescueTask.Status.Pending)
                .notes(notes)
                .build();

        incident.setRescueAssigned(true);
        incidentRepo.save(incident);
        return rescueRepo.save(task);
    }

    public List<RescueTask> findByStatus(RescueTask.Status status) {
        if (status == null) return rescueRepo.findAll();
        return rescueRepo.findByStatus(status);
    }

    @Transactional
    public RescueTask updateStatus(UUID id, RescueTask.Status status, Instant completionDatetime) {
        RescueTask t = rescueRepo.findById(id).orElseThrow();
        t.setStatus(status);
        if (completionDatetime != null) t.setCompletionDatetime(completionDatetime);
        return rescueRepo.save(t);
    }
}
