package com.sparta.project.User;

import com.sparta.project.JWT.JwtTokenProvider;
import com.sparta.project.JWT.RefreshToken;
import com.sparta.project.JWT.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        // 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // 인증 성공 시 SecurityContextHolder에 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 액세스 토큰 발급
        String jwt = jwtTokenProvider.createAccessToken(authentication.getName());

        // Refresh Token 생성 및 저장
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authentication.getName());

        // 응답으로 JWT 액세스 토큰과 Refresh 토큰 반환
        return Map.of(
                "accessToken", jwt,
                "refreshToken", refreshToken.getToken()
        );
    }

    @PostMapping("/logout")
    public String logout(Principal principal) {
        String username = principal.getName();
        refreshTokenService.invalidateRefreshToken(username);
        return "로그아웃되었습니다.";
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshTokenService.validateRefreshToken(refreshToken)) {
            String username = jwtTokenProvider.getUsername(refreshToken);
            String newAccessToken = jwtTokenProvider.createAccessToken(username);
            return Map.of("accessToken", newAccessToken);
        } else {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
    }
}
