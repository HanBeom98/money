package com.sparta.project.JWT;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import com.sparta.project.Service.CustomUserDetailsService;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final String secretKey = "your-secret-key"; // 실제로는 환경변수나 설정파일에서 불러오는 것이 좋습니다.
    private final CustomUserDetailsService userDetailsService;
    private final long accessTokenValidityInMilliseconds = 3600000; // 1 hour
    private final long refreshTokenValidityInMilliseconds = 86400000; // 1 day (24 hours)

    public String createAccessToken(String username) {
        return createToken(username, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(String username) {
        return createToken(username, refreshTokenValidityInMilliseconds);
    }

    private String createToken(String username, long validityInMilliseconds) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long getRefreshTokenValidityInMilliseconds() {
        return refreshTokenValidityInMilliseconds;
    }

    public Mono<Authentication> getAuthentication(String authToken) {
        return userDetailsService.findByUsername(getUsername(authToken))
                .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
    }
}
