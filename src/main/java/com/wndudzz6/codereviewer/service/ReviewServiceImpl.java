package com.wndudzz6.codereviewer.service;


import com.wndudzz6.codereviewer.domain.Review;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.domain.platform.BojDifficulty;
import com.wndudzz6.codereviewer.domain.platform.Difficulty;
import com.wndudzz6.codereviewer.domain.platform.ProgrammersDifficulty;
import com.wndudzz6.codereviewer.dto.ReviewRequest;
import com.wndudzz6.codereviewer.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements  ReviewService {

    private final ReviewRepository reviewRepository;

    public Review saveReview(ReviewRequest dto, Submission submission) {
        Difficulty difficulty;
        switch (dto.getPlatform().toUpperCase()) {
            case "BOJ" -> difficulty = BojDifficulty.valueOf(dto.getDifficulty());
            case "PROGRAMMERS" -> difficulty = ProgrammersDifficulty.valueOf(dto.getDifficulty());
            default -> throw new IllegalArgumentException("지원하지 않는 플랫폼입니다: " + dto.getPlatform());
        }

        Review review = Review.builder()
                .summary(dto.getSummary())
                .strategy(dto.getStrategy())
                .codeQuality(dto.getCodeQuality())
                .improvement(dto.getImprovement())
                .timeComplexity(dto.getTimeComplexity())
                .difficulty(difficulty)
                .tags(dto.getTags())
                .submission(submission)
                .reviewedAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    @Override @Transactional
    public void deleteById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        reviewRepository.delete(review);
    }


    @Override
    public List<Review> findReviewListBySubmissionId(Long submissionId) {
        return reviewRepository.findReviewListBySubmissionId(submissionId);
    }

    @Override
    public List<Review> findReviewListByUserId(Long userId) {
        return reviewRepository.findReviewListBySubmission_User_Id(userId);
    }


}
