package com.alalay.backend.services;

import com.alalay.backend.model.Calamity;
import com.alalay.backend.repository.CalamityRepository;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CalamityService {

    private final CalamityRepository calamityRepo;

    public CalamityService(CalamityRepository calamityRepo) {
        this.calamityRepo = calamityRepo;
    }

    /* =============================
       BASIC GETTERS
       ============================= */
    public List<Calamity> findAll() {
        return calamityRepo.findAll();
    }

    public Optional<Calamity> findById(UUID id) {
        return calamityRepo.findById(id);
    }


    /* =============================
       CREATE CALAMITY
       ============================= */
    @Transactional
    public Calamity createCalamity(
            Instant startDate,
            String description,
            String calamityCategory,
            Instant reportedEndDate,
            String affectedAreasWKT // WKT string
    ) {
        Geometry geom = null;
        if (affectedAreasWKT != null) {
            try {
                geom = new WKTReader().read(affectedAreasWKT);
            } catch (Exception e) {
                throw new RuntimeException("Invalid WKT Geometry format");
            }
        }

        Calamity calamity = Calamity.builder()
                .id(UUID.randomUUID())
                .startDate(startDate)
                .description(description)
                .calamityCategory(calamityCategory)
                .reportedEndDate(reportedEndDate)
                .affectedAreas(geom)
                .createdAt(Instant.now())
                .build();

        return calamityRepo.save(calamity);
    }


    /* =============================
       UPDATE CALAMITY (Partial)
       ============================= */
    @Transactional
    public Calamity updateCalamity(
            UUID id,
            Instant startDate,
            String description,
            String calamityCategory,
            Instant reportedEndDate,
            String affectedAreasWKT
    ) {
        Calamity calamity = calamityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Calamity not found"));

        if (startDate != null) calamity.setStartDate(startDate);
        if (description != null) calamity.setDescription(description);
        if (calamityCategory != null) calamity.setCalamityCategory(calamityCategory);
        if (reportedEndDate != null) calamity.setReportedEndDate(reportedEndDate);

        if (affectedAreasWKT != null) {
            try {
                Geometry geom = new WKTReader().read(affectedAreasWKT);
                calamity.setAffectedAreas(geom);
            } catch (Exception e) {
                throw new RuntimeException("Invalid WKT Geometry");
            }
        }

        return calamityRepo.save(calamity);
    }


    /* =============================
       DELETE CALAMITY
       ============================= */
    @Transactional
    public boolean deleteCalamity(UUID id) {
        if (!calamityRepo.existsById(id)) return false;

        calamityRepo.deleteById(id);
        return true;
    }
}
