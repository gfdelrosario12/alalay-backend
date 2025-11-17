package com.alalay.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "response_log")
public class ResponseLog {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "calamity_id", nullable = false)
    private Calamity calamity;

    private String description;
    private String logLocation;
    private LocalDateTime logDatetime = LocalDateTime.now();
    private String responseDetails;
}