package com.wndudzz6.codereviewer.service;


import com.wndudzz6.codereviewer.domain.Review;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.domain.platform.BojDifficulty;
import com.wndudzz6.codereviewer.dto.ReviewRequest;
import com.wndudzz6.codereviewer.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) //Mockito 단위테스트, 가짜 객체
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl; //구현체 테스트

    @Test
    @DisplayName(" ReviewService: 리뷰를 저장하는 기능 정상 작동")
    void save_review_correct(){
        //given
        ReviewRequest dto = ReviewRequest.builder()
                .summary("DP 유한 냅색")
                .strategy("dp테이블을 역방향으로 채운다")
                .codeQuality("상")
                .improvement("확장포인트 : 개수 제한이 클 경우 분할가능 여부 고려")
                .timeComplexity("O(n * k)")
                .difficulty(BojDifficulty.GOLD_5.name())
                .platform("BOJ")
                .tags(List.of("DP", "Knapsack"))
                .build();

        Submission submission = Submission.builder().id(1L).build();


        //Review 저장하면 동일한 Review 반환 가정
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        Review saved = reviewServiceImpl.saveReview(dto, submission);

        //then
        ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository, times(1)).save(captor.capture());

        Review captured = captor.getValue();
        assertThat(captured.getSummary()).isEqualTo("DP 유한 냅색");
        assertThat(captured.getStrategy()).isEqualTo("dp테이블을 역방향으로 채운다");
        assertThat(captured.getSubmission()).isEqualTo(submission);
        assertThat(saved).isEqualTo(captured);

    }

    @Test
    @DisplayName("ReviewService: 리뷰 삭제 - 정상 작동")
    void delete_review_correct() {
        // given
        Long reviewId = 1L;
        Review mockReview = Review.builder().id(reviewId).build();
        when(reviewRepository.findById(reviewId)).thenReturn(java.util.Optional.of(mockReview));

        // when
        reviewServiceImpl.deleteById(reviewId);

        // then
        verify(reviewRepository).delete(mockReview);
    }

    @Test
    @DisplayName("ReviewService: 리뷰 삭제 - 없는 ID로 삭제 시 예외 발생")
    void delete_review_not_found() {
        // given
        Long invalidId = 99L;
        when(reviewRepository.findById(invalidId)).thenReturn(java.util.Optional.empty());

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> reviewServiceImpl.deleteById(invalidId));
    }

    @Test
    @DisplayName("ReviewService: 제출 ID로 리뷰 목록 조회")
    void find_reviews_by_submissionId() {
        // given
        Long submissionId = 1L;
        List<Review> mockList = List.of(Review.builder().id(1L).build());
        when(reviewRepository.findReviewListBySubmissionId(submissionId)).thenReturn(mockList);

        // when
        List<Review> result = reviewServiceImpl.findReviewListBySubmissionId(submissionId);

        // then
        assertThat(result).hasSize(1);
        verify(reviewRepository).findReviewListBySubmissionId(submissionId);
    }

    @Test
    @DisplayName("ReviewService: 유저 ID로 리뷰 목록 조회")
    void find_reviews_by_userId() {
        // given
        Long userId = 10L;
        List<Review> mockList = List.of(Review.builder().id(2L).build());
        when(reviewRepository.findReviewListBySubmission_User_Id(userId)).thenReturn(mockList);

        // when
        List<Review> result = reviewServiceImpl.findReviewListByUserId(userId);

        // then
        assertThat(result).hasSize(1);
        verify(reviewRepository).findReviewListBySubmission_User_Id(userId);
    }



}
