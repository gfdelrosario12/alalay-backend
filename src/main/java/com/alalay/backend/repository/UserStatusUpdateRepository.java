package com.alalay.backend.repository;

import com.alalay.backend.model.UserStatusUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserStatusUpdateRepository extends JpaRepository<UserStatusUpdate, UUID> {}
