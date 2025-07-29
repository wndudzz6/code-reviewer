package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.dto.GptSubmissionReviewResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wndudzz6.codereviewer.domain.Language;
import com.wndudzz6.codereviewer.domain.Review;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.domain.platform.Difficulty;
import com.wndudzz6.codereviewer.domain.platform.Platform;
import com.wndudzz6.codereviewer.dto.ReviewRequest;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;
import com.wndudzz6.codereviewer.repository.ReviewRepository;
import com.wndudzz6.codereviewer.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Profile("noredis")
@RequiredArgsConstructor
@Slf4j
public class GptCodeReviewerServiceNoRedis implements AICodeReviewerService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final SubmissionRepository submissionRepository;
    private final ReviewRepository reviewRepository;
    private final UserServiceImpl userService;

    @Value("${openai.api.key}")
    private String apiKey;

    @Transactional
    public Submission createSubmissionWithReview(String code, Long userId) {
        log.info("🔄 Redis 미사용 → GPT 직접 호출");

        String prompt = buildPrompt(code);
        GptSubmissionReviewResponse response = callGpt(prompt);

        User user = userService.findById(userId);

        Submission submission = Submission.builder()
                .title(response.getSubmission().getTitle())
                .problemUrl(response.getSubmission().getProblemUrl())
                .platform(Platform.valueOf(response.getSubmission().getPlatform()))
                .language(Language.valueOf(response.getSubmission().getLanguage()))
                .code(code)
                .user(user)
                .submittedAt(LocalDateTime.now())
                .build();

        Submission saved = submissionRepository.save(submission);

        Review review = Review.builder()
                .summary(response.getReview().getSummary())
                .strategy(response.getReview().getStrategy())
                .codeQuality(response.getReview().getCodeQuality())
                .improvement(response.getReview().getImprovement())
                .timeComplexity(response.getReview().getTimeComplexity())
                .difficulty(Difficulty.from(response.getReview().getDifficulty()))
                .tags(response.getReview().getTags())
                .submission(saved)
                .reviewedAt(LocalDateTime.now())
                .build();

        Review savedReview = reviewRepository.save(review);
        saved.setReview(savedReview);

        return saved;
    }

    private String buildPrompt(String code) {
        return String.format("""
            아래 코드는 사용자의 알고리즘 풀이 코드입니다.
            아래 정보를 추출해줘. JSON만 반환해. 설명은 하지 마.

            {
              "submission": {
                "title": "문제 제목",
                "problemUrl": "문제 URL",
                "platform": "BOJ",
                "language": "JAVA"
              },
              "review": {
                "summary": "한 줄 요약",
                "strategy": "풀이 전략",
                "codeQuality": "코드 품질",
                "improvement": "개선점",
                "timeComplexity": "시간복잡도",
                "difficulty": "GOLD_5",
                "tags": ["DP", "배낭문제"]
              }
            }

            -- 코드 시작 --
            %s
            -- 코드 끝 --
        """, code);
    }

    private GptSubmissionReviewResponse callGpt(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions", httpEntity, String.class
        );

        try {
            String content = objectMapper
                    .readTree(response.getBody())
                    .get("choices").get(0)
                    .get("message").get("content").asText();

            int start = content.indexOf("{");
            int end = content.lastIndexOf("}");
            String json = content.substring(start, end + 1);
            return objectMapper.readValue(json, GptSubmissionReviewResponse.class);

        } catch (Exception e) {
            log.error("GPT 응답 파싱 실패", e);
            throw new RuntimeException("GPT 응답 파싱 실패", e);
        }
    }

    @Override
    public ReviewRequest review(SubmissionRequest request) {
        Submission submission = createSubmissionWithReview(request.getCode(), request.getUserId());
        Review review = submission.getReview();

        return ReviewRequest.builder()
                .summary(review.getSummary())
                .strategy(review.getStrategy())
                .codeQuality(review.getCodeQuality())
                .improvement(review.getImprovement())
                .timeComplexity(review.getTimeComplexity())
                .difficulty(review.getDifficulty().name())
                .tags(review.getTags())
                .build();
    }

    @Override
    public ReviewRequest createReviewForSubmission(SubmissionRequest request) {
        return review(request);
    }
}