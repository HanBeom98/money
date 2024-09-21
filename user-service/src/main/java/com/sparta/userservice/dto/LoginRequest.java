package com.sparta.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    // Getter와 Setter 추가
    private String username;
    private String password;

}
