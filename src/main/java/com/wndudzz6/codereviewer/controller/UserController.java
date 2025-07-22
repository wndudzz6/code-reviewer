package com.wndudzz6.codereviewer.controller;


import com.wndudzz6.codereviewer.config.jwt.JwtProvider;
import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.dto.UserLoginRequest;
import com.wndudzz6.codereviewer.dto.UserRegisterRequest;
import com.wndudzz6.codereviewer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterRequest request) {
        User saved =  userService.register(request);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        User user = userService.login(request);

        //JWT 토큰 발급 후 return
        String token = jwtProvider.generateToken(user.getEmail());
        return ResponseEntity.ok(token);
    }
}
