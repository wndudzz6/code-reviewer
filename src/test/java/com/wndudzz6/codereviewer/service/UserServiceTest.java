package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.dto.UserLoginRequest;
import com.wndudzz6.codereviewer.dto.UserRegisterRequest;
import com.wndudzz6.codereviewer.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("회원가입 성공")
    void register_success() {
        // given
        String rawPassword = "1234";
        String encodedPassword = "encoded1234";
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        User result = userService.register(
                new UserRegisterRequest("test@email.com", rawPassword, "tester"));

        // then
        assertThat(result.getPassword()).isEqualTo(encodedPassword);
        assertThat(result.getNickname()).isEqualTo("tester");
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        // given
        String rawPassword = "1234";
        String encodedPassword = "encoded1234";

        User user = User.builder().id(1L).email("test@email.com").password(encodedPassword).build();

        when(userRepository.findByEmail("test@email.com")).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // when
        User result = userService.login(new UserLoginRequest("test@email.com", rawPassword));

        // then
        assertThat(result).isEqualTo(user);
    }

    @Test
    @DisplayName("로그인 실패 - 이메일 존재하지 않음")
    void login_fail_email_not_found() {
        // given
        when(userRepository.findByEmail("none@email.com")).thenReturn(java.util.Optional.empty());

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.login(new UserLoginRequest("none@email.com", "pw")));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void login_fail_password_mismatch() {
        // given
        User user = User.builder().email("test@email.com").password("encoded1234").build();
        when(userRepository.findByEmail("test@email.com")).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encoded1234")).thenReturn(false);

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.login(new UserLoginRequest("test@email.com", "wrongPassword")));
    }

    @Test
    @DisplayName("사용자 ID로 조회 성공")
    void findById_success() {
        // given
        User user = User.builder().id(1L).build();
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        // when
        User result = userService.findById(1L);

        // then
        assertThat(result).isEqualTo(user);
    }

    @Test
    @DisplayName("사용자 ID로 조회 실패 - 존재하지 않음")
    void findById_not_found() {
        when(userRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.findById(99L));
    }


    @Test
    @DisplayName("닉네임 수정 성공")
    void updateNickname_success() {
        // given
        User user = User.builder().id(1L).email("test@email.com").nickname("oldNick").password("encoded").build();
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        User updated = userService.updateNickname(1L, "newNick");

        // then
        assertThat(updated.getNickname()).isEqualTo("newNick");
    }

    @Test
    @DisplayName("계정 삭제 성공")
    void deleteUser_success() {
        // given
        User user = User.builder().id(1L).email("test@email.com").nickname("tester").build();
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        // when
        userService.deleteUser(1L);

        // then
        verify(userRepository, times(1)).delete(user);
    }
}
