package com.sparta.project;

import com.sparta.project.User.User;
import com.sparta.project.User.UserDto;
import com.sparta.project.User.UserRepository;
import com.sparta.project.User.UserService;
import com.sparta.project.User.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("현재 사용자 조회")
    void getCurrentUser() {
        // Mock UserDetails
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Set the authentication in SecurityContextHolder
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock the user repository
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setRole(User.Role.USER); // Role 설정
        mockUser.setUserType(UserType.LOCAL);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));

        // Execute the method under test
        Mono<User> userMono = userService.getCurrentUser();

        // Verify the results
        StepVerifier.create(userMono)
                .expectNextMatches(user -> user.getEmail().equals("test@example.com"))
                .verifyComplete();
    }

    @Test
    @DisplayName("사용자 등록 테스트")
    void registerUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setUserType("LOCAL");
        userDto.setRole("USER"); // Role 설정

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setRole(User.Role.USER); // Role 설정
        mockUser.setUserType(UserType.LOCAL);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        UserDto savedUserDto = userService.registerUser(userDto);

        verify(userRepository, times(1)).save(any(User.class));
        assert savedUserDto.getEmail().equals("test@example.com");
    }

    @Test
    @DisplayName("모든 사용자 조회 테스트")
    void findAllUsers() {
        List<User> mockUsers = new ArrayList<>();
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setRole(User.Role.USER);
        user1.setUserType(UserType.LOCAL);
        mockUsers.add(user1);

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setRole(User.Role.USER);
        user2.setUserType(UserType.LOCAL);
        mockUsers.add(user2);

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<UserDto> userDtos = userService.findAllUsers();

        assertEquals(2, userDtos.size());
        assertEquals("user1@example.com", userDtos.get(0).getEmail());
        assertEquals("user2@example.com", userDtos.get(1).getEmail());
    }

    @Test
    @DisplayName("사용자 저장 테스트")
    void saveUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setName("Test User");
        userDto.setRole("USER");
        userDto.setUserType("LOCAL");

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setName("Test User");
        mockUser.setRole(User.Role.USER);
        mockUser.setUserType(UserType.LOCAL);

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        UserDto savedUserDto = userService.saveUser(userDto);

        assertEquals("test@example.com", savedUserDto.getEmail());
        assertEquals("Test User", savedUserDto.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("User 객체를 UserDto로 변환 테스트")
    public void convertToDto() { // 메서드 접근자를 public으로 변경
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRole(User.Role.USER);
        user.setUserType(UserType.LOCAL);

        UserDto userDto = userService.convertToDto(user);

        assertEquals(1L, userDto.getId());
        assertEquals("Test User", userDto.getName());
        assertEquals("test@example.com", userDto.getEmail());
        assertEquals("USER", userDto.getRole());
        assertEquals("LOCAL", userDto.getUserType());
    }

    @Test
    @DisplayName("UserDto 객체를 User로 변환 테스트")
    void convertToEntity() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Test User");
        userDto.setEmail("test@example.com");
        userDto.setRole("USER");
        userDto.setUserType("LOCAL");

        User user = userService.convertToEntity(userDto);

        assertEquals(1L, user.getId());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(User.Role.USER, user.getRole());
        assertEquals(UserType.LOCAL, user.getUserType());
    }
}
