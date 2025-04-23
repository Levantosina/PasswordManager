package com.password.auth;


import com.password.jwt.JWTUtil;
import com.password.model.UserEntity;
import com.password.service.OwnDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    public AuthenticationService(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }


    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.userName(),
                        authenticationRequest.password()
                )
        );
        OwnDetails ownUserDetails = (OwnDetails) authentication.getPrincipal();
        UserEntity userEntity = ownUserDetails.getUserEntity();

        String role = userEntity.getRole();

        String token = jwtUtil.issueToken(userEntity.getUserName(), role);

        return new AuthenticationResponse(token);
    }
}

