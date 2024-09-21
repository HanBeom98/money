package com.sparta.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "`user`") // 예약어 처리
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 기본 생성자 (JPA에서 필요)
    public User() {
    }

    // 사용자 ID를 인자로 받는 생성자
    public User(Long userId) {
        this.id = userId;
    }
}
