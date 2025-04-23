package com.password.repository;

import com.password.model.PasswordEntity;
import com.password.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {
   List<PasswordEntity> findByUser(UserEntity userId);
}
