package com.password.service;

import com.password.dto.UserDTO;
import com.password.mapper.UserDTOMapper;
import com.password.model.AuditLogEntity;
import com.password.model.PasswordEntity;
import com.password.model.UserEntity;
import com.password.repository.AuditLogRepository;
import com.password.repository.PasswordRepository;
import com.password.repository.UserRepository;
import com.password.request.UserRegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.nio.file.AccessDeniedException;
import java.util.List;




@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final ExtractUserIdFromContext extractUserIdFromContext;
    private final PasswordRepository passwordRepository;
    private final AuditLogRepository auditLogRepository;


    public void createUser(UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.existsByUserName(userRegistrationRequest.username())) {
            throw new RuntimeException("User with this username already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRegistrationRequest.username());
        userEntity.setPassword(passwordEncoder.encode(userRegistrationRequest.password()));
        userEntity.setRole("USER");
        userRepository.save(userEntity);
    }


    public void deleteUser() throws AccessDeniedException {

        Long userId = extractUserIdFromContext.extractUserIdFromContext();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with [%s] not found.".formatted(userId)));

        if (!user.getUserId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to delete this user");
        }

        List<PasswordEntity> passwords = passwordRepository.findAllByUserAndDeletedFalse(user);
        passwords.forEach(p -> p.setDeleted(true));
        passwordRepository.saveAll(passwords);

        List<AuditLogEntity> logs = auditLogRepository.findAllByUserAndDeletedFalse(user);
        logs.forEach(log -> log.setDeleted(true));
        auditLogRepository.saveAll(logs);

        user.setDeleted(true);
        userRepository.save(user);
    }

    public void restoreUser(Long userId) {
        UserEntity user = userRepository.findByUserIdAndDeletedTrue(userId)
                .orElseThrow(() -> new RuntimeException("Deleted user not found with ID: " + userId));

        user.setDeleted(false);
        userRepository.save(user);
    }

}

