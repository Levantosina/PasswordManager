package com.password.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PasswordUpdateRequest(
        @JsonProperty("currentServiceName")
        String serviceName,
        Long passwordId,
        @JsonProperty("newServiceName")
        String newServiceName,
        @JsonProperty("newPassword")
        String newPassword
) {
}
