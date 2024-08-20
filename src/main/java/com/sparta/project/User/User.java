package com.sparta.project.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")  // 테이블 이름을 'users'로 명시
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String lastLoginIp;
    private String lastLoginAgent;
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER, SELLER
    }
}
