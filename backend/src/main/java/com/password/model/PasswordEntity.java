package com.password.model;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "password_entity", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "service_name"})
})
@SQLDelete(sql = "UPDATE password_entity SET deleted = true WHERE password_id = ?")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class PasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordId;
    private String serviceName;
    private String encryptedPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;
    @Column(name="deleted")
    private boolean deleted = false;

    @PostLoad
    public void applyDeletedFilter() {
        if (this.deleted) {
            Hibernate.initialize(this);
        }
    }
}
