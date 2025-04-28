package com.password.service;

import com.password.model.UserEntity;
import com.password.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@AllArgsConstructor
public class ExtractUserIdFromContext {
    private final UserRepository userRepository;

    public Long extractUserIdFromContext() throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new AccessDeniedException("No authenticated user found");
        }


        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String authenticatedEmail = userDetails.getUsername();

        UserEntity authenticatedUser = userRepository.findByUserName(authenticatedEmail)
                .orElseThrow(() -> new AccessDeniedException("Authenticated user not found"));

        return authenticatedUser.getUserId();
    }
}
