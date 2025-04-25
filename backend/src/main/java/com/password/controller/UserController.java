package com.password.controller;

import com.password.dto.UserDTO;
import com.password.jwt.JWTUtil;
import com.password.request.UserRegistrationRequest;
import com.password.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;




    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {

        userService.createUser(userRegistrationRequest);
        return ResponseEntity.ok().body("User successfully created");

    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser()  {

        try {
            userService.deleteUser();
            return ResponseEntity.ok("User deleted successfully");
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/{userId}/restore")
    public ResponseEntity<String> restoreUser(@PathVariable Long userId) {
        userService.restoreUser(userId);
        return ResponseEntity.ok("User account restored successfully.");
    }
}
