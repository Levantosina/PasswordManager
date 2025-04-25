package com.password.repository;

import com.password.model.PasswordEntity;
import com.password.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {
   List<PasswordEntity> findAllByUser(UserEntity userId);
   Optional<PasswordEntity> findByUserAndServiceName(UserEntity user, String serviceName);
   List<PasswordEntity> findAllByUserAndDeletedFalse(UserEntity user);
   Optional<PasswordEntity> findByUserAndServiceNameAndDeletedFalse(UserEntity user, String serviceName);
}
