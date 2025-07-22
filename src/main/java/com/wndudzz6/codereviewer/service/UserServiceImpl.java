package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.dto.UserLoginRequest;
import com.wndudzz6.codereviewer.dto.UserRegisterRequest;
import com.wndudzz6.codereviewer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("이메일이 존재하지 않습니다"));
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        return user;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()->(new IllegalArgumentException("사용자를 찾을 수 없습니다.")));
    }
}
