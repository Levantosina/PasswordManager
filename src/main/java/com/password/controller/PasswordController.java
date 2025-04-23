package com.password.controller;

import com.password.dto.PasswordDTO;
import com.password.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/passwords")
@AllArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping("/user/{userId}")
    public List<PasswordDTO> getAllPasswordsByUser(@PathVariable Long userId){
        return passwordService.getAllPasswordByUser(userId);
    }
    @PostMapping("/user/{userId}")
    public void addPassword(@PathVariable Long userId, @RequestParam String serviceName,
                            @RequestParam String encryptedPassword) {
        passwordService.createPassword(userId, serviceName, encryptedPassword);
    }
}
