package com.wndudzz6.codereviewer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wndudzz6.codereviewer.domain.Language;
import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.domain.platform.Platform;
import com.wndudzz6.codereviewer.dto.GptResponse;
import com.wndudzz6.codereviewer.dto.ReviewRequest;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;
import com.wndudzz6.codereviewer.repository.ReviewRepository;
import com.wndudzz6.codereviewer.repository.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GptCodeReviewerServiceTest {

    @Mock private RestTemplate restTemplate;
    @Mock private UserServiceImpl userService;
    @Mock private SubmissionRepository submissionRepository;
    @Mock private ReviewRepository reviewRepository;
    @Mock private RedisTemplate<String, Object> redisTemplate;
    @Mock private ValueOperations<String, Object> valueOperations;
    @InjectMocks private GptCodeReviewerService gptCodeReviewerService;
//    @Mock private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // RedisTemplate이 opsForValue() 호출 시 valueOperations를 반환하게 설정
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        // ObjectMapper는 실제 인스턴스 할당 (mock 불필요)
        gptCodeReviewerService = new GptCodeReviewerService(
                new ObjectMapper(),   // ✅ 여기!
                restTemplate,
                submissionRepository,
                reviewRepository,
                userService,
                redisTemplate
        );
    }

    @Test
    @DisplayName("GPT 응답이 ReviewRequest로 잘 파싱되는지 확인")
    void review_successfully_parses_gpt_response() throws Exception {
        // given
        SubmissionRequest submissionRequest = SubmissionRequest.builder()
                .title("DP 문제")
                .platform(Platform.BOJ)
                .language(Language.JAVA)
                .code("public class Main {}")
                .userId(1L)
                .build();

        User mockUser = new User(1L, "test@example.com", "password", "codinggosu", List.of());
        when(userService.findById(submissionRequest.getUserId())).thenReturn(mockUser);

        // Redis에 캐시가 없다고 가정
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.opsForValue().get(any())).thenReturn(null);

        // GPT 응답 문자열 (실제 JSON 포함된 string)
        // 1. mockGptContent는 그대로 둠 (String)
        String mockGptContent = """
        {
        "submission": {
            "title": "DP 문제",
            "problemUrl": "https://boj.kr/1234",
            "platform": "BOJ",
            "language": "JAVA"
            },
        "review": {
            "summary": "DP 문제입니다.",
            "strategy": "바텀업 접근",
            "codeQuality": "가독성 좋음",
            "improvement": "예외처리 추가 필요",
            "timeComplexity": "O(n * k)",
            "difficulty": "GOLD_5",
            "tags": ["DP", "Knapsack"]
           }
        }
        """;

// 2. JSON 문자열로 변환 (이게 핵심!)
        String escapedContent = new ObjectMapper().writeValueAsString(mockGptContent);

// 3. OpenAI 응답 포맷 생성
        String wrappedResponse = """
{
  "choices": [
    {
      "message": {
        "content": %s
      }
    }
  ]
}
""".formatted(escapedContent);

        ResponseEntity<String> entity = new ResponseEntity<>(wrappedResponse, HttpStatus.OK);
        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(String.class)))
                .thenReturn(entity);

        // Submission 저장 mock
        when(submissionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Review 저장 mock
        when(reviewRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        ReviewRequest result = gptCodeReviewerService.createReviewForSubmission(submissionRequest);

        // then
        assertThat(result.getSummary()).isEqualTo("DP 문제입니다.");
        assertThat(result.getDifficulty()).isEqualTo("GOLD_5");
        assertThat(result.getTags()).contains("DP", "Knapsack");
    }
}

