package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.domain.Review;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.dto.ReviewRequest;

import java.util.List;

public interface ReviewService {
    Review saveReview(ReviewRequest dto, Submission submission);
    void deleteById(Long id);
    List<Review> findReviewListBySubmissionId(Long submissionId);
    List<Review> findReviewListByUserId(Long userId);
}
