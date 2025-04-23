package com.password.service;

import com.password.dto.UserDTO;
import com.password.jwt.JWTUtil;
import com.password.mapper.UserDTOMapper;
import com.password.model.UserEntity;
import com.password.repository.UserRepository;

import com.password.request.UserRegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;


    public List<UserDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public void createUser(UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.existsByUserName(userRegistrationRequest.username())) {
            throw new RuntimeException("User with this username already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRegistrationRequest.username());
        userEntity.setPassword(passwordEncoder.encode(userRegistrationRequest.password()));
        userEntity.setRole("USER");
        userRepository.save(userEntity);
    }
}
