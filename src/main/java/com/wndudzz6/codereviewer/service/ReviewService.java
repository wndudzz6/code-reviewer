package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.domain.Review;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.dto.ReviewRequest;

public interface ReviewService {
    Review saveReview(ReviewRequest dto, Submission submission);
}
