package com.alalay.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "calamities")
public class Calamity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private LocalDateTime startDate;
    private String description;
    private String calamityCategory;
    private LocalDateTime reportedEndDate;
    private String affectedAreas;
}