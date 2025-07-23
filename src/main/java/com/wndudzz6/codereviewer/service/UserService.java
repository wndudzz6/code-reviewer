package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.dto.UserLoginRequest;
import com.wndudzz6.codereviewer.dto.UserRegisterRequest;


public interface UserService {
    User register(UserRegisterRequest request);
    User login(UserLoginRequest request);
    //List<Submission> getMySubmissions(Long userId);
    User findById(Long id);
    User updateNickname(Long userId, String newNickname);
    void deleteUser(Long userId);

}
