package com.alalay.backend.repository;

import com.alalay.backend.model.RescueTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RescueTaskRepository extends JpaRepository<RescueTask, UUID> {
    List<RescueTask> findByStatus(RescueTask.Status status);
}
