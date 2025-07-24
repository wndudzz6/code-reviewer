package com.wndudzz6.codereviewer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wndudzz6.codereviewer.domain.Review;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.dto.GptResponse;
import com.wndudzz6.codereviewer.dto.ReviewRequest;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;
import com.wndudzz6.codereviewer.repository.ReviewRepository;
import com.wndudzz6.codereviewer.repository.SubmissionRepository;
import com.wndudzz6.codereviewer.service.AICodeReviewerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptCodeReviewerService implements AICodeReviewerService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ReviewService reviewService;  // ReviewService 주입
    private final SubmissionRepository submissionRepository;
    private final ReviewRepository reviewRepository;
    private final UserServiceImpl userService;

    @Value("${openai.api.key}")
    private String apiKey;

    @Override
    @Transactional
    public ReviewRequest createReviewForSubmission(SubmissionRequest request) {
        // 1. Submission 저장 (먼저 제출 정보만 저장)
        User currentUser = userService.findById(request.getUserId());
        Submission submission = saveSubmission(request, currentUser); // User 연결

        // 2. GPT API 요청 생성
        String prompt = buildPrompt(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<GptResponse> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions", httpEntity, GptResponse.class
        );

        // 3. 응답 파싱
        String content = extractContent(response);
        ReviewRequest reviewRequest = parseToReviewRequest(content);

        // 4. Review 저장
        // Review 엔티티는 ReviewService에서 저장
        Review savedReview = reviewService.saveReview(reviewRequest, submission);

        // 5. Submission에 Review 연결
        submission.setReview(savedReview);
        submissionRepository.save(submission);

        return reviewRequest;
    }

    private Submission saveSubmission(SubmissionRequest request, User user) {
        // Submission 객체를 생성하고 저장하는 로직
        Submission submission = new Submission(
                null, // ID는 자동 생성
                request.getTitle(),
                request.getProblemUrl(),
                request.getPlatformEnum(),
                request.getLanguageEnum(),
                request.getCode(),
                user, // User 정보는 서비스에서 처리해야 함
                null, //Review타입
                LocalDateTime.now()
        );

        return submissionRepository.save(submission);
    }

    private String buildPrompt(SubmissionRequest request) {
        return String.format(""" 
            아래는 사용자가 알고리즘 문제를 해결한 코드입니다.

            - 문제 제목: %s
            - 플랫폼: %s
            - 언어: %s

            다음 코드를 기반으로 다음 항목을 JSON 형식으로 추출해줘.
            단, 난이도(difficulty)는 다음 형식으로 표기해야 한다:

            - 백준(BOJ): BRONZE_5 ~ DIAMOND_1
            - 프로그래머스(Programmers): LEVEL_0 ~ LEVEL_5

            {
              "summary": "한 줄 요약",
              "strategy": "풀이 전략 설명",
              "codeQuality": "코드 품질 평가",
              "improvement": "개선할 점",
              "timeComplexity": "시간복잡도",
              "difficulty": "GOLD_5",
              "platform": "BOJ 또는 PROGRAMMERS",
              "tags": ["그리디", "정렬", "DP"]
            }

            -- 코드 시작 --
            %s
            -- 코드 끝 --
            """, request.getTitle(), request.getPlatform(), request.getLanguage(), request.getCode());
    }

    private String extractContent(ResponseEntity<GptResponse> response) {
        return response.getBody()
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }

    private ReviewRequest parseToReviewRequest(String content) {
        try {
            return objectMapper.readValue(content, ReviewRequest.class);
        } catch (Exception e) {
            log.error("GPT 응답 파싱 실패: {}", content);
            throw new RuntimeException("GPT 응답 파싱 실패", e);
        }
    }

    @Override
    public ReviewRequest review(SubmissionRequest request) {
        return null;
    }
}


