package com.password.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record PasswordRegistrationRequest(
        @NotBlank(message = "Service name is required")
        @JsonProperty("serviceName")
        String serviceName,
        @NotBlank(message = "Password is required")
        @JsonProperty("encryptedPassword")
        String password
)
{}

