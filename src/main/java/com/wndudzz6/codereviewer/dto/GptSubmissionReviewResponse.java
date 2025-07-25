package com.wndudzz6.codereviewer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//파싱을 위한 DTO
@Getter @Setter
public class GptSubmissionReviewResponse {
    private SubmissionPart submission;
    private ReviewPart review;

    @Getter @Setter
    public static class SubmissionPart {
        private String title;
        private String problemUrl;
        private String platform;
        private String language;
    }

    @Getter @Setter
    public static class ReviewPart {
        private String summary;
        private String strategy;
        private String codeQuality;
        private String improvement;
        private String timeComplexity;
        private String difficulty;
        private List<String> tags;
    }
}
