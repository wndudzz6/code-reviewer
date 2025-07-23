package com.wndudzz6.codereviewer.controller;


import com.wndudzz6.codereviewer.config.jwt.JwtProvider;
import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.dto.UserLoginRequest;
import com.wndudzz6.codereviewer.dto.UserRegisterRequest;
import com.wndudzz6.codereviewer.dto.UserResponse;
import com.wndudzz6.codereviewer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        User saved = userService.register(request);
        return ResponseEntity.ok(UserResponse.from(saved));
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        User user = userService.login(request);

        //JWT 토큰 발급 후 return
        String token = jwtProvider.generateToken(user.getEmail());
        return ResponseEntity.ok(token);
    }

    // 사용자 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    //닉네임 수정
    @PutMapping("/{id}/nickname")
    public ResponseEntity<UserResponse> updateNickname(
            @PathVariable Long id,
            @RequestBody String newNickname) {
        User updated = userService.updateNickname(id, newNickname);
        return ResponseEntity.ok(UserResponse.from(updated));
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
