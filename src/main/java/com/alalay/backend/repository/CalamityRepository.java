package com.alalay.backend.repository;

import com.alalay.backend.model.Calamity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CalamityRepository extends JpaRepository<Calamity, UUID> {}
