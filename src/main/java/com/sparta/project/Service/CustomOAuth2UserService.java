package com.sparta.project.Service;

import com.sparta.project.User.User;
import com.sparta.project.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 사용자 정보 가져오기
        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) properties.get("nickname");
        String picture = properties != null ? (String) properties.get("profile_image") : null;

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email not found from OAuth2 provider");
        }

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(nickname);
            user.setProfilePicture(picture);
            userRepository.save(user);
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(nickname);
            newUser.setProfilePicture(picture);
            newUser.setRole(User.Role.USER);
            newUser.setPassword(null);  // 비밀번호를 설정하지 않음
            userRepository.save(newUser);
        }

        return oAuth2User;
    }
}
