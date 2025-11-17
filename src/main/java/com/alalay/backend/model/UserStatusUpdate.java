package com.alalay.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_status_update")
public class UserStatusUpdate {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "calamity_id", nullable = false)
    private Calamity calamity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String currentSituation;
    private LocalDateTime updateDatetime = LocalDateTime.now();
    private String location;
}