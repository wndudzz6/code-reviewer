package com.wndudzz6.codereviewer.controller;

import com.wndudzz6.codereviewer.domain.Review;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.dto.ReviewRequest;
import com.wndudzz6.codereviewer.dto.ReviewResponse;
import com.wndudzz6.codereviewer.service.ReviewServiceImpl;
import com.wndudzz6.codereviewer.service.SubmissionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final SubmissionServiceImpl submissionService;
    private final ReviewServiceImpl reviewService;

    //리뷰 생성
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @RequestBody ReviewRequest request,
            @RequestParam Long submissionId
    ) {
        Submission submission = submissionService.findById(submissionId);
        Review saved = reviewService.saveReview(request, submission);
        return ResponseEntity.ok(ReviewResponse.from(saved));
    }

    //리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build(); // 상태 코드 204 반환
    }

    @GetMapping("/submission/{submissionId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsBySubmission(@PathVariable Long submissionId) {
        List<Review> reviews = reviewService.findReviewListBySubmissionId(submissionId);
        return ResponseEntity.ok(reviews.stream().map(ReviewResponse::from).toList());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUser(@PathVariable Long userId) {
        List<Review> reviews = reviewService.findReviewListByUserId(userId);
        return ResponseEntity.ok(reviews.stream().map(ReviewResponse::from).toList());
    }


}
