package com.sparta.project.JWT;

import com.sparta.project.User.User;
import com.sparta.project.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        String token = jwtTokenProvider.createRefreshToken(username);
        RefreshToken refreshToken = new RefreshToken(token, user, System.currentTimeMillis() + jwtTokenProvider.getRefreshTokenValidityInMilliseconds());

        refreshTokenRepository.deleteByUser(user); // 기존 리프레시 토큰 삭제
        return refreshTokenRepository.save(refreshToken);
    }

    public boolean validateRefreshToken(String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token);
        if (refreshTokenOpt.isPresent()) {
            RefreshToken refreshToken = refreshTokenOpt.get();
            // 만료 시간을 추가로 확인
            return refreshToken.getExpiryDate() > System.currentTimeMillis() && jwtTokenProvider.validateToken(token);
        }
        return false;
    }

    @Transactional // 트랜잭션 애노테이션 추가
    public void invalidateRefreshToken(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        refreshTokenRepository.deleteByUser(user);
    }
}
