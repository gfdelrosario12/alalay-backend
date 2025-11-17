package com.alalay.backend.repository;

import com.alalay.backend.model.ResponseLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResponseLogRepository extends JpaRepository<ResponseLog, UUID> {}
