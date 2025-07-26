package com.wndudzz6.codereviewer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class ReviewSubmissionDTO {

    private Long submissionId;
    private String title;
    private String problemUrl;
    private String code;
    private String language;
    private String platform;

    private String summary;
    private String strategy;
    private String codeQuality;
    private String improvement;
    private String timeComplexity;
    private String difficulty;
    private List<String> tags;
    private LocalDateTime reviewedAt;

    // 생성자
    public ReviewSubmissionDTO(Long submissionId, String title, String problemUrl, String code, String language, String platform,
                               String summary, String strategy, String codeQuality, String improvement, String timeComplexity,
                               String difficulty, List<String> tags, LocalDateTime reviewedAt) {
        this.submissionId = submissionId;
        this.title = title;
        this.problemUrl = problemUrl;
        this.code = code;
        this.language = language;
        this.platform = platform;
        this.summary = summary;
        this.strategy = strategy;
        this.codeQuality = codeQuality;
        this.improvement = improvement;
        this.timeComplexity = timeComplexity;
        this.difficulty = difficulty;
        this.tags = tags;
        this.reviewedAt = reviewedAt;
    }
    public static ReviewSubmissionDTO fromEntity(com.wndudzz6.codereviewer.domain.Submission submission) {
        var review = submission.getReview();
        if (review == null) {
            throw new IllegalArgumentException("리뷰가 없는 제출입니다: submissionId=" + submission.getId());
        }

        return new ReviewSubmissionDTO(
                submission.getId(),
                submission.getTitle(),
                submission.getProblemUrl(),
                submission.getCode(),
                submission.getLanguage().name(),
                submission.getPlatform().name(),
                review.getSummary(),
                review.getStrategy(),
                review.getCodeQuality(),
                review.getImprovement(),
                review.getTimeComplexity(),
                review.getDifficulty().name(),
                review.getTags(),
                review.getReviewedAt()
        );
    }

}

