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

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)  // 비밀번호를 nullable로 설정하여 소셜 로그인 시 비밀번호 없이 생성 가능
    private String password;

    private String lastLoginIp;
    private String lastLoginAgent;
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserType userType; // 추가된 필드

    public enum Role {
        USER, SELLER
    }
}

