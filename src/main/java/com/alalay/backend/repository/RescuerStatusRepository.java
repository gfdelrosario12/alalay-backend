package com.alalay.backend.repository;

import com.alalay.backend.model.RescuerStatus;
import com.alalay.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RescuerStatusRepository extends JpaRepository<RescuerStatus, UUID> {

    // Find by User entity
    Optional<RescuerStatus> findByRescuer(User rescuer);

    // OR find by rescuer UUID directly
    @Query("SELECT rs FROM RescuerStatus rs WHERE rs.rescuer.id = :rescuerId")
    Optional<RescuerStatus> findByRescuerId(@Param("rescuerId") UUID rescuerId);

    // Existing method to find nearest available rescuers
    @Query(value = """
            SELECT rs.*
            FROM rescuer_status rs
            WHERE rs.is_available = true
            ORDER BY ST_Distance(rs.last_known_location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326))
            LIMIT :limit
            """, nativeQuery = true)
    List<RescuerStatus> findNearestAvailable(@Param("lon") double lon,
                                             @Param("lat") double lat,
                                             @Param("limit") int limit);
}
