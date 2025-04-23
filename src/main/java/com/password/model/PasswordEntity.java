package com.password.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordId;
    private String serviceName;
    private String encryptedPassword;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;
}
