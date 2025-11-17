package com.alalay.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "affected_individuals_log")
public class AffectedIndividualLog {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "calamity_id", nullable = false)
    private Calamity calamity;

    @Column(nullable = false)
    private LocalDateTime instanceDatetime;

    private String description;
    private String instanceLocation;
    private Boolean isResponded = false;
    private LocalDateTime responseDatetime;

    @ManyToOne
    @JoinColumn(name = "rescuer_id")
    private User rescuer;

    private String otherAffectedMembers;
    private String otherImportantDetails;
}