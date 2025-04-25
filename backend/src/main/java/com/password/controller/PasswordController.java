package com.password.controller;

import com.password.dto.PasswordDTO;
import com.password.request.PasswordRegistrationRequest;
import com.password.request.PasswordUpdateRequest;
import com.password.service.ExtractUserIdFromContext;
import com.password.service.PasswordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/passwords")
@AllArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;
    private final ExtractUserIdFromContext extractUserIdFromContext;

    @GetMapping("/user/get")
    public List<PasswordDTO> getAllPasswordsByUser() {
        try {
            Long userId = extractUserIdFromContext.extractUserIdFromContext();
            return passwordService.getAllPasswordByUser();
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/user/password")
    public void createPassword(@Valid @RequestBody PasswordRegistrationRequest request) {
        try {
            passwordService.createPasswordSection(request);
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<String> updatePasswordSection(@Valid @RequestBody PasswordUpdateRequest request) {
        try {
            passwordService.updatePasswordSection(request);
            return ResponseEntity.ok("Password updated successfully.");
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/user/delete/{passwordId}")
    public ResponseEntity<?> deletePassword(@PathVariable("passwordId") Long passwordId) {
        try {
            passwordService.deletePassword(passwordId);
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
}
