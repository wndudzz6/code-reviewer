package com.wndudzz6.codereviewer.dto;


import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest {
    private String email;
    public String password;
    //jwt토큰 필드 추가
}
