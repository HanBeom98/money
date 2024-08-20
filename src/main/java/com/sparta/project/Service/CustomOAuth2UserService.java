package com.sparta.project.Service;

import com.sparta.project.User.User;
import com.sparta.project.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture"); // 프로필 사진 URL

        // 사용자 정보가 이미 있는지 확인
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            // 사용자 정보 업데이트
            User user = existingUser.get();
            user.setName(name); // 이름 업데이트
            user.setProfilePicture(picture); // 프로필 사진 업데이트 (필드 추가 필요)
            userRepository.save(user);
        } else {
            // 없으면 새로운 사용자 저장
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setProfilePicture(picture); // 프로필 사진 저장
            newUser.setRole(User.Role.USER);  // 기본 역할 설정
            userRepository.save(newUser);
        }

        return oAuth2User;
    }
}
