package com.wndudzz6.codereviewer.dto;

import com.wndudzz6.codereviewer.domain.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReviewResponse {

    private Long id;
    private String summary;
    private String strategy;
    private String codeQuality;
    private String improvement;
    private String timeComplexity;
    private String difficulty;
    private List<String> tags;
    private LocalDateTime reviewedAt;

    private Long submissionId;
    private String submissionTitle;

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .summary(review.getSummary())
                .strategy(review.getStrategy())
                .codeQuality(review.getCodeQuality())
                .improvement(review.getImprovement())
                .timeComplexity(review.getTimeComplexity())
                .difficulty(review.getDifficulty().toString()) // Difficulty → String
                .tags(review.getTags())
                .reviewedAt(review.getReviewedAt())
                .submissionId(review.getSubmission().getId())
                .submissionTitle(review.getSubmission().getTitle())
                .build();
    }
}
