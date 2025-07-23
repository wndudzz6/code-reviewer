package com.wndudzz6.codereviewer.dto;

import com.wndudzz6.codereviewer.domain.User;

public record UserResponse(Long id, String email, String nickname) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getNickname());
    }
}
