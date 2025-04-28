package com.password.repository;

import com.password.model.AuditLogEntity;
import com.password.model.PasswordEntity;
import com.password.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity,Long> {
    List<AuditLogEntity> findAllByUserAndDeletedFalse(UserEntity user);
    List<AuditLogEntity> findAllByUser(UserEntity userId);
}
