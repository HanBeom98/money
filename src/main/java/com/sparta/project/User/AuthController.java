package com.sparta.project.User;

import com.sparta.project.JWT.JwtTokenProvider;
import com.sparta.project.JWT.RefreshToken;
import com.sparta.project.JWT.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final ReactiveAuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Mono<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        return authenticationManager.authenticate(authToken)
                .map(authentication -> {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = jwtTokenProvider.createAccessToken(authentication.getName());
                    RefreshToken refreshToken = refreshTokenService.createRefreshToken(authentication.getName());

                    return Map.of(
                            "accessToken", jwt,
                            "refreshToken", refreshToken.getToken()
                    );
                });
    }

    @PostMapping("/logout")
    public Mono<String> logout(Principal principal) {
        return Mono.justOrEmpty(principal)
                .map(Principal::getName)
                .flatMap(username -> {
                    refreshTokenService.invalidateRefreshToken(username);
                    return Mono.just("로그아웃되었습니다.");
                });
    }

    @PostMapping("/refresh")
    public Mono<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        return Mono.justOrEmpty(refreshToken)
                .filter(refreshTokenService::validateRefreshToken)
                .map(jwtTokenProvider::getUsername)
                .map(username -> {
                    String newAccessToken = jwtTokenProvider.createAccessToken(username);
                    return Map.of("accessToken", newAccessToken);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.")));
    }
}
