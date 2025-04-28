package com.password.controller;

import com.password.dto.AuditLogDTO;
import com.password.service.AuditLogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/auditLog")
@AllArgsConstructor
public class AuditLogController {
    private final AuditLogService auditLogService;

    @GetMapping
    public List<AuditLogDTO> getAuditLog() {
        try {
            return auditLogService.getAuditLog();
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
}
