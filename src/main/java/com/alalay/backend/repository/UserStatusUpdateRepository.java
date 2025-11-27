package com.alalay.backend.repository;

import com.alalay.backend.model.UserStatusUpdate;
import com.alalay.backend.model.User;
import com.alalay.backend.model.Calamity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserStatusUpdateRepository extends JpaRepository<UserStatusUpdate, UUID> {

    // Find all status updates for a specific user
    List<UserStatusUpdate> findByUser(User user);

    // Find all status updates for a specific user and calamity
    List<UserStatusUpdate> findByUserAndCalamity(User user, Calamity calamity);

}
