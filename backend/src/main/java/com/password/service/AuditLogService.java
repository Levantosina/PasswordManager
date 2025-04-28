package com.password.service;

import com.password.dto.AuditLogDTO;
import com.password.mapper.AuditLogDTOMapper;
import com.password.model.AuditLogEntity;
import com.password.model.PasswordEntity;
import com.password.model.UserEntity;
import com.password.repository.AuditLogRepository;
import com.password.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AuditLogService {

    private AuditLogRepository auditLogRepository;
    private final ExtractUserIdFromContext extractUserIdFromContext;
    private final UserRepository userRepository;
    private final AuditLogDTOMapper auditLogDTOMapper;


    public void logAction(String action, UserEntity user, PasswordEntity password) {
        AuditLogEntity log = new AuditLogEntity();
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user);
        log.setPassword(password);
        auditLogRepository.save(log);
    }

    public List<AuditLogDTO> getAuditLog() throws AccessDeniedException {
        Long userId = extractUserIdFromContext.extractUserIdFromContext();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        List<AuditLogEntity> auditLogEntities = auditLogRepository.findAllByUser(user);
        return auditLogEntities.stream()
                .map(auditLogDTOMapper)
                .collect(Collectors.toList());
    }
}
