package com.alalay.backend.repository;

import com.alalay.backend.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, UUID> {

    // All incidents under a specific calamity
    List<Incident> findByCalamity_Id(UUID calamityId);

    // Unresponded incidents (rescue not yet assigned)
    List<Incident> findByRescueAssignedFalse();

    // Within calamity and not yet responded
    List<Incident> findByCalamity_IdAndRescueAssignedFalse(UUID calamityId);

    // Filter by rescuer
    List<Incident> findByRescuer_Id(UUID rescuerId);
}
