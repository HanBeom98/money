package com.sparta.project.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto saveUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public UserDto registerUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (userDto.getUserType().equals(UserType.LOCAL.name())) {
            if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다.");
            }
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return saveUser(userDto);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setProfilePicture(user.getProfilePicture());
        dto.setRole(user.getRole().name());
        dto.setUserType(user.getUserType().name());
        return dto;
    }

    private User convertToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setProfilePicture(dto.getProfilePicture());
        user.setRole(User.Role.valueOf(dto.getRole()));
        user.setUserType(UserType.valueOf(dto.getUserType()));
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        return user;
    }
}
