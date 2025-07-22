package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;
import com.wndudzz6.codereviewer.repository.SubmissionRepository;
import com.wndudzz6.codereviewer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    @Override
    public Submission submit(Long userId, SubmissionRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        Submission submission = Submission.builder()
                .title(request.getTitle())
                .problemUrl(request.getProblemUrl())
                .language(request.getLanguage())
                .code(request.getCode())
                .user(user)
                .submittedAt(LocalDateTime.now())
                .build();

        return  submissionRepository.save(submission);
    }

    @Override
    public Submission findById(Long id) {
        return submissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("제출 기록을 찾을 수 없습니다."));
    }



    @Override
    public List<Submission> getSubmissionByUserId(Long userId) {
        return submissionRepository.findAllByUserId(userId);
    }
}
