package com.password.service;

import com.password.model.AuditLogEntity;
import com.password.model.PasswordEntity;
import com.password.model.UserEntity;
import com.password.repository.AuditLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class AuditLogService {

    private AuditLogRepository auditLogRepository;

    public void logAction(String action, UserEntity user, PasswordEntity password) {
        AuditLogEntity log = new AuditLogEntity();
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user);
        log.setPassword(password);
        auditLogRepository.save(log);
    }
}
