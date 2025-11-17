package com.alalay.backend.repository;

import com.alalay.backend.model.AffectedIndividualLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AffectedIndividualLogRepository extends JpaRepository<AffectedIndividualLog, UUID> {}
