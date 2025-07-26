package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.dto.ReviewSubmissionDTO;
import com.wndudzz6.codereviewer.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewSubmissionQueryService {

    private final SubmissionRepository submissionRepository;

    public List<ReviewSubmissionDTO> getAll() {
        return submissionRepository.findAll().stream()
                .filter(submission -> submission.getReview() != null) // 리뷰가 있는 제출만
                .map(ReviewSubmissionDTO::fromEntity) // DTO로 변환
                .toList();
    }
    // 제출 ID로 하나 조회
    public ReviewSubmissionDTO getBySubmissionId(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("제출 ID에 해당하는 제출을 찾을 수 없습니다."));
        if (submission.getReview() == null) {
            throw new IllegalArgumentException("해당 제출에는 리뷰가 없습니다.");
        }
        return ReviewSubmissionDTO.fromEntity(submission);
    }

    //유저 ID로 전체 제출 조회
    public List<ReviewSubmissionDTO> getExelListByUserId(Long userId) {
        return submissionRepository.findSubmissionListByUserId(userId).stream()
                .filter(sub -> sub.getReview() != null)
                .map(ReviewSubmissionDTO::fromEntity)
                .toList();
    }
}
