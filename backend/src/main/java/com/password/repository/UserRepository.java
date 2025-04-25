package com.password.repository;

import com.password.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity>findByUserName(String username);
    boolean existsByUserName(String userName);
    Optional<UserEntity> findByUserIdAndDeletedTrue(Long userId);
}
