package com.wndudzz6.codereviewer.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//회원가입 요청 시 필요한 정보
public class UserRegisterRequest {
    private String email;
    private String password;
    private String nickname;
}
