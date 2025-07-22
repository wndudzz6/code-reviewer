package com.wndudzz6.codereviewer.repository;

import com.wndudzz6.codereviewer.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    //리뷰 조회
    Optional<Review> findBySubmissionId(Long submissionId);

    //전체 리뷰 조회
    List<Review> findAllBySubmission_UserId(Long userId);
}
