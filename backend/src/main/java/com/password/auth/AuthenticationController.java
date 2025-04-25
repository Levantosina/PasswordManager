package com.password.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            log.info("Login attempt for email: {}", authenticationRequest.userName());

            AuthenticationResponse response = authenticationService.login(authenticationRequest);
            log.info("Token generated: {}", response.token());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Login failed for email {}: {}", authenticationRequest.userName(), e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthenticationResponse("Invalid credentials"));
        }
    }
}
