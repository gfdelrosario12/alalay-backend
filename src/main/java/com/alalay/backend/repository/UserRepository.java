package com.alalay.backend.repository;

import com.alalay.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(User.Role role);

    /**
     * Find nearest available rescuers using PostGIS distance calculation.
     */
    @Query(value = """
            SELECT u.*
            FROM users u
            JOIN rescuer_status rs ON rs.rescuer_id = u.id
            WHERE rs.is_available = true
            ORDER BY ST_DistanceSphere(
                ST_SetSRID(ST_MakePoint(:lon, :lat), 4326),
                rs.last_known_location
            )
            LIMIT :limit
            """, nativeQuery = true)
    List<User> findNearestAvailableRescuers(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("limit") int limit
    );
}
