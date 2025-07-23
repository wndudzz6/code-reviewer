package com.wndudzz6.codereviewer.dto;

import com.wndudzz6.codereviewer.domain.Language;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.domain.platform.Platform;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SubmissionResponse {
    private Long id;
    private String title;
    private String problemUrl;
    private Platform platform;

    @Enumerated(EnumType.STRING)
    private Language language;

    private String code;
    private LocalDateTime submittedAt;
    private Long userId;
    private String userEmail;

    public static SubmissionResponse from(Submission submission) {
        return SubmissionResponse.builder()
                .id(submission.getId())
                .title(submission.getTitle())
                .problemUrl(submission.getProblemUrl())
                .platform(submission.getPlatform())
                .language(submission.getLanguage())
                .code(submission.getCode())
                .submittedAt(submission.getSubmittedAt())
                .userId(submission.getUser().getId())
                .userEmail(submission.getUser().getEmail())
                .build();
    }
}
