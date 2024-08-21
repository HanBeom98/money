package com.sparta.project.config;

import com.sparta.project.JWT.JwtAuthenticationFilter;
import com.sparta.project.JWT.JwtTokenProvider;
import com.sparta.project.Service.CustomUserDetailsService;
import com.sparta.project.Service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService, CustomOAuth2UserService customOAuth2UserService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/**").permitAll()
                        .requestMatchers("/api/videos/**").permitAll()
                        .requestMatchers("/api/advertisements/**").permitAll()
                        .requestMatchers("/api/auth/login", "/oauth2/**").permitAll()  // 로컬 로그인과 카카오 로그인 허용
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2  // OAuth2 로그인 설정
                        .defaultSuccessUrl("/api/users", true)  // 로그인 성공 후 리디렉션될 URL 설정
                        .failureUrl("/login?error=true")  // 로그인 실패 시 리디렉션될 URL 설정
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)  // 카카오 사용자 정보 서비스
                        )
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
