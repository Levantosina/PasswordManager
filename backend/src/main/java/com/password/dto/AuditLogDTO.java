package com.password.dto;

import java.time.LocalDateTime;

public record AuditLogDTO(
        String action,
        LocalDateTime timestamp,
        String userName
) { }
