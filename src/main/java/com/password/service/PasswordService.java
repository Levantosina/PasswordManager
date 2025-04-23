package com.password.service;

import com.password.dto.PasswordDTO;
import com.password.mapper.PasswordDTOMapper;
import com.password.model.PasswordEntity;
import com.password.model.UserEntity;
import com.password.repository.PasswordRepository;
import com.password.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PasswordService {
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordDTOMapper passwordDTOMapper;
    private final UserRepository userRepository;

    public PasswordService(PasswordRepository passwordRepository, PasswordEncoder passwordEncoder, PasswordDTOMapper passwordDTOMapper, UserRepository userRepository) {
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordDTOMapper = passwordDTOMapper;
        this.userRepository = userRepository;
    }


    public List<PasswordDTO> getAllPasswordByUser(Long userId) {

       UserEntity user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

       return passwordRepository.findByUser(user)
               .stream()
               .map(passwordDTOMapper)
               .collect(Collectors.toList());
   }

    public void createPassword(Long userId, String serviceName,String encryptedPassword) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User with [%s] not found.".formatted(userId)));

        String encryptedPasswordHash = passwordEncoder.encode(encryptedPassword);

        PasswordEntity passwordEntity = PasswordEntity.builder()
                .serviceName(serviceName)
                .encryptedPassword(encryptedPasswordHash)
                .user(user)
                .build();
        passwordRepository.save(passwordEntity);
    }

    public void deletePassword(Long userId) {
        PasswordEntity password = passwordRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("Password with [%s] not found.".formatted(userId)));

        passwordRepository.delete(password);
    }
}
