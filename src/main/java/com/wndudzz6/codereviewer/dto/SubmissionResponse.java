package com.wndudzz6.codereviewer.dto;

import com.wndudzz6.codereviewer.domain.Submission;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SubmissionResponse {
    private Long id;
    private String title;
    private String problemUrl;
    private String language;
    private String code;
    private LocalDateTime submittedAt;
    private Long userId;
    private String userEmail;

    public static SubmissionResponse from(Submission submission) {
        return SubmissionResponse.builder()
                .id(submission.getId())
                .title(submission.getTitle())
                .problemUrl(submission.getProblemUrl())
                .language(submission.getLanguage())
                .code(submission.getCode())
                .submittedAt(submission.getSubmittedAt())
                .userId(submission.getUser().getId())
                .userEmail(submission.getUser().getEmail())
                .build();
    }
}
