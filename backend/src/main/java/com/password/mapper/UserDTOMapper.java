package com.password.mapper;

import com.password.dto.UserDTO;
import com.password.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<UserEntity, UserDTO> {

    @Override
    public UserDTO apply(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getUserName()
        );
    }
}
