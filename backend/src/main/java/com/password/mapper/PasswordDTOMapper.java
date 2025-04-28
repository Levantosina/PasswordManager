package com.password.mapper;

import com.password.dto.PasswordDTO;
import com.password.model.PasswordEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PasswordDTOMapper implements Function<PasswordEntity,PasswordDTO> {
    @Override
    public PasswordDTO apply(PasswordEntity passwordEntity) {
        return new PasswordDTO(
                passwordEntity.getPasswordId(),
                passwordEntity.getServiceName(),
                passwordEntity.getEncryptedPassword()
        );
    }
}
