package com.password.service;
import com.password.model.UserEntity;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collections;

@Getter
public class OwnDetails extends User {
    private final UserEntity userEntity;

    public OwnDetails(UserEntity userEntity) {
        super(userEntity.getUserName(), userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole())));
        this.userEntity = userEntity;
    }

}
