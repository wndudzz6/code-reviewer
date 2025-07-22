package com.wndudzz6.codereviewer.service;


import com.wndudzz6.codereviewer.domain.Review;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.dto.ReviewRequest;
import com.wndudzz6.codereviewer.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements  ReviewService {

    private final ReviewRepository reviewRepository;

    public Review saveReview(ReviewRequest dto, Submission submission) {
        Review review = Review.builder()
                .summary(dto.getSummary())
                .strategy(dto.getStrategy())
                .codeQuality(dto.getCodeQuality())
                .improvement(dto.getImprovement())
                .timeComplexity(dto.getTimeComplexity())
                .difficulty(dto.getDifficulty())
                .tags(dto.getTags())
                .submission(submission)
                .reviewedAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }
}
