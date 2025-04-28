package com.password.service;


import com.password.dto.PasswordDTO;
import com.password.mapper.PasswordDTOMapper;
import com.password.model.PasswordEntity;
import com.password.model.UserEntity;
import com.password.repository.PasswordRepository;
import com.password.repository.UserRepository;
import com.password.request.PasswordRegistrationRequest;
import com.password.request.PasswordUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PasswordService {
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordDTOMapper passwordDTOMapper;
    private final UserRepository userRepository;
    private final ExtractUserIdFromContext extractUserIdFromContext;
    private final AuditLogService auditLogService;


    public List<PasswordDTO> getAllPasswordByUser() throws AccessDeniedException {

        Long userId = extractUserIdFromContext.extractUserIdFromContext();
        UserEntity user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        List<PasswordEntity> allPasswords = passwordRepository.findAllByUser(user);
        return allPasswords.stream()
                .filter(p -> !p.isDeleted())
                .map(passwordDTOMapper)
                .collect(Collectors.toList());
    }

    public void createPasswordSection(PasswordRegistrationRequest passwordRegistrationRequest) throws AccessDeniedException {

        Long userId = extractUserIdFromContext.extractUserIdFromContext();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User with [%s] not found.".formatted(userId)));

        String encryptedPasswordHash = passwordEncoder.encode(passwordRegistrationRequest.password());

        PasswordEntity passwordEntity = PasswordEntity.builder()
                .serviceName(passwordRegistrationRequest.serviceName())
                .encryptedPassword(encryptedPasswordHash)
                .user(user)
                .build();
        passwordRepository.save(passwordEntity);

        auditLogService.logAction("CREATED_PASSWORD",user,passwordEntity);
    }
    public void updatePasswordSection(PasswordUpdateRequest updateRequest) throws AccessDeniedException {
        Long userId = extractUserIdFromContext.extractUserIdFromContext();


        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));


        PasswordEntity password = passwordRepository
                .findByUserAndServiceNameAndDeletedFalse(user, updateRequest.serviceName())
                .orElseThrow(() -> new RuntimeException("Password for service not found"));

        password.setEncryptedPassword(passwordEncoder.encode(updateRequest.newPassword()));
        password.setServiceName(updateRequest.newServiceName());

        passwordRepository.save(password);

        auditLogService.logAction("UPDATED_PASSWORD", user, password);
    }

    public void deletePassword(Long passwordId) throws AccessDeniedException {
        Long userId = extractUserIdFromContext.extractUserIdFromContext();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        PasswordEntity password = passwordRepository.findById(passwordId)
                .orElseThrow(()->new RuntimeException("Password with [%s] not found.".formatted(passwordId)));
        if (!password.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to delete this password");
        }

        passwordRepository.delete(password);
        auditLogService.logAction("DELETED_PASSWORD",user,password);
    }
}
