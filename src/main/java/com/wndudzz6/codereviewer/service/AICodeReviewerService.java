package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.domain.Review;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.dto.ReviewRequest;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;

public interface AICodeReviewerService {

    //AI한테 코드 리뷰 요청 -> ReviewRequest로 변환
    ReviewRequest review(SubmissionRequest request);
    ReviewRequest createReviewForSubmission(SubmissionRequest request);

    //Submission + Review를 동시에 생성
    Submission createSubmissionWithReview(String code, Long userId);
}
