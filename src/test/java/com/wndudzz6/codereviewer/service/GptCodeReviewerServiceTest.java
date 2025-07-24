package com.wndudzz6.codereviewer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wndudzz6.codereviewer.domain.Language;
import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.domain.platform.Platform;
import com.wndudzz6.codereviewer.dto.GptResponse;
import com.wndudzz6.codereviewer.dto.ReviewRequest;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;
import com.wndudzz6.codereviewer.repository.SubmissionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Mock
    private RestTemplate restTemplate;  // RestTemplate을 Mock으로 설정

    @Mock
    private UserServiceImpl userService;  // UserService를 Mock으로 설정

    @Mock
    private SubmissionRepository submissionRepository;  // SubmissionRepository를 Mock으로 설정

    @InjectMocks
    private GptCodeReviewerService gptCodeReviewerService;  // GptCodeReviewerService에 mock 의존성 주입

    @Test
    @DisplayName("GPT 응답이 ReviewRequest로 잘 파싱되는지 확인")
    void review_successfully_parses_gpt_response() throws Exception {
        // given
        SubmissionRequest submissionRequest = SubmissionRequest.builder()
                .title("DP 문제")
                .platform(Platform.BOJ)  // Platform enum 처리
                .language(Language.JAVA)  // Language enum 처리
                .code("public class Main {}")
                .userId(1L)  // userId 추가
                .build();

        // Mock User 객체
        User mockUser = new User(1L, "test@example.com", "password", "codinggosu", List.of());

        // Mock UserService의 findById 메서드
        when(userService.findById(submissionRequest.getUserId())).thenReturn(mockUser);

        // Mock GPT 응답
        String mockContent = """
        {
          "summary": "DP 문제입니다.",
          "strategy": "바텀업 접근",
          "codeQuality": "가독성 좋음",
          "improvement": "예외처리 추가 필요",
          "timeComplexity": "O(n * k)",
          "difficulty": "GOLD_5",
          "platform": "BOJ",
          "tags": ["DP", "Knapsack"]
        }
        """;

        GptResponse mockResponse = new GptResponse();
        GptResponse.GptMessage message = new GptResponse.GptMessage();
        message.setContent(mockContent);
        GptResponse.GptChoice choice = new GptResponse.GptChoice();
        choice.setMessage(message);
        mockResponse.setChoices(List.of(choice));

        ResponseEntity<GptResponse> entity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(GptResponse.class)))
                .thenReturn(entity);

        // Mock SubmissionRepository.save() 메서드
        when(submissionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        ReviewRequest result = gptCodeReviewerService.createReviewForSubmission(submissionRequest);

        // then
        assertThat(result.getSummary()).isEqualTo("DP 문제입니다.");
        assertThat(result.getDifficulty()).isEqualTo("GOLD_5");
        assertThat(result.getPlatform()).isEqualTo("BOJ");
        assertThat(result.getTags()).contains("DP", "Knapsack");
    }
}
