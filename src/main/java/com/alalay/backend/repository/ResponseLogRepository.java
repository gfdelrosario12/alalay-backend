package com.alalay.backend.repository;

import com.alalay.backend.model.ResponseLog;
import com.alalay.backend.model.User;
import com.alalay.backend.model.Calamity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResponseLogRepository extends JpaRepository<ResponseLog, UUID> {
    List<ResponseLog> findByUser(User user);
    List<ResponseLog> findByCalamity(Calamity calamity);
}
