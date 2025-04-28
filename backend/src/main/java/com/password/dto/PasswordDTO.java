package com.password.dto;

public record PasswordDTO (
        Long passwordId,
        String serviceName,
        String encryptedPassword
){}
