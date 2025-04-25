package com.password.mapper;

import com.password.dto.AuditLogDTO;
import com.password.model.AuditLogEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AuditLogDTOMapper implements Function<AuditLogEntity, AuditLogDTO> {
    @Override
    public AuditLogDTO apply(AuditLogEntity auditLogEntity) {
        return new AuditLogDTO(
                auditLogEntity.getAction(),
                auditLogEntity.getTimestamp(),
                auditLogEntity.getUser().getUserName()
        );
    }
}
