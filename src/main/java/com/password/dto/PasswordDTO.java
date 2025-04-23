package com.password.dto;

public record PasswordDTO (
        String serviceName,
        String encryptedPassword
){}
