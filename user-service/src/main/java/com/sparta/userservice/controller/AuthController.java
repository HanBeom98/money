package com.sparta.userservice.controller;

import com.sparta.userservice.dto.LoginRequest;
import com.sparta.userservice.jwt.JwtTokenProvider;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // JWT 로그인
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String role = "USER"; // 기본적으로 일반 사용자

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(username, role);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    // 카카오 소셜 로그인 성공 후 처리
    @GetMapping("/oauth2/success")
    public Map<String, String> socialLogin(OAuth2AuthenticationToken authentication) {
        // 카카오에서 받아온 사용자 정보
        String username = authentication.getPrincipal().getAttribute("kakao_account").toString();
        String role = "USER"; // 소셜 사용자도 기본적으로 USER 역할 부여

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(username, role);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout() {
        return "Logged out successfully";
    }
}
