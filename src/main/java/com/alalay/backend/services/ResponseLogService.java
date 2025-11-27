package com.alalay.backend.services;

import com.alalay.backend.model.ResponseLog;
import com.alalay.backend.model.User;
import com.alalay.backend.model.Calamity;
import com.alalay.backend.repository.ResponseLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseLogService {

    private final ResponseLogRepository responseLogRepository;

    public List<ResponseLog> getAllLogs() {
        return responseLogRepository.findAll();
    }

    public List<ResponseLog> getLogsByUser(User user) {
        return responseLogRepository.findByUser(user);
    }

    public List<ResponseLog> getLogsByCalamity(Calamity calamity) {
        return responseLogRepository.findByCalamity(calamity);
    }
}
