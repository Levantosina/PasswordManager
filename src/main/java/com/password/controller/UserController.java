package com.password.controller;

import com.password.dto.UserDTO;
import com.password.jwt.JWTUtil;
import com.password.request.UserRegistrationRequest;
import com.password.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {

        userService.createUser(userRegistrationRequest);
        return ResponseEntity.ok().body("User successfully created");

    }
}
