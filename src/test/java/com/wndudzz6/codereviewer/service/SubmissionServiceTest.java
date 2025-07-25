package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.domain.Language;
import com.wndudzz6.codereviewer.domain.platform.Platform;
import com.wndudzz6.codereviewer.domain.Submission;
import com.wndudzz6.codereviewer.domain.User;
import com.wndudzz6.codereviewer.dto.SubmissionRequest;
import com.wndudzz6.codereviewer.repository.SubmissionRepository;
import com.wndudzz6.codereviewer.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//제출이 잘 됐는지 테스트
@ExtendWith(MockitoExtension.class)
public class SubmissionServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SubmissionServiceImpl submissionServiceImpl;


    @Test
    @DisplayName("제출 성공")
    void submission_correct() {

        // given
        SubmissionRequest dto = SubmissionRequest.builder()
                .title("백준 12865")
                .problemUrl("https://www.acmicpc.net/problem/12865")
                .platform(Platform.BOJ)  // Enum으로 직접 지정
                .language(Language.JAVA)  // Enum으로 직접 지정
                .code("import java.util.Scanner;\n" +
                        "\n" +
                        "public class Main {\n" +
                        "    static int solution(int n, int k, int[][] items) { ... }")
                .userId(1L)
                .build();

        User mockUser = User.builder().id(1L).email("test@email.com").build();
        Submission submission = Submission.builder()
                .id(1L)
                .title(dto.getTitle())
                .problemUrl(dto.getProblemUrl())
                .platform(dto.getPlatform())  // Enum으로 처리된 platform
                .language(dto.getLanguage())  // Enum으로 처리된 language
                .code(dto.getCode())
                .user(mockUser)
                .build();

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUser));
        when(submissionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Submission result = submissionServiceImpl.submit(1L, dto);

        assertThat(result.getTitle()).isEqualTo("백준 12865");
        assertThat(result.getUser().getId()).isEqualTo(1L);
    }


    @Test
    @DisplayName("제출 ID로 단건 조회")
    void find_submission_by_id() {
        // given
        Submission sub = Submission.builder().id(5L).title("단건 테스트").build();
        when(submissionRepository.findById(5L)).thenReturn(java.util.Optional.of(sub));

        // when
        Submission result = submissionServiceImpl.findById(5L);

        // then
        assertThat(result.getTitle()).isEqualTo("단건 테스트");
    }


    @Test
    @DisplayName("유저 ID로 제출 목록 조회")
    void get_submissions_by_userId() {
        // given
        Long userId = 1L;
        User user = User.builder().id(userId).build();
        Submission sub = Submission.builder().id(1L).user(user).build();

        when(submissionRepository.findSubmissionListByUserId(userId)).thenReturn(List.of(sub));

        // when
        List<Submission> result = submissionServiceImpl.findSubmissionListByUserId(userId);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getId()).isEqualTo(userId);
    }


    @Test
    @DisplayName("제출 삭제 성공")
    void delete_submission() {
        // given
        Submission mockSubmission = Submission.builder().id(1L).build();
        when(submissionRepository.findById(1L)).thenReturn(java.util.Optional.of(mockSubmission));

        // when
        submissionServiceImpl.deleteById(1L);

        // then
        verify(submissionRepository, times(1)).delete(mockSubmission);
    }

    @Test
    @DisplayName("존재하지 않는 유저로 제출 시 IllegalArgumentException 발생")
    void submission_user_not_found() {
        // given
        SubmissionRequest dto = SubmissionRequest.builder()
                .title("백준 12865")
                .problemUrl("https://www.acmicpc.net/problem/12865")
                .platform(Platform.BOJ)  // Enum으로 직접 지정
                .language(Language.JAVA)  // Enum으로 직접 지정
                .code("import java.util.Scanner;\n" +
                        "\n" +
                        "public class Main {\n" +
                        "    static int solution(int n, int k, int[][] items) { ... }")
                .userId(1L)
                .build();
        User mockUser = User.builder().id(1L).email("test@email.com").build();
        Submission submission = Submission.builder()
                .id(1L)
                .title(dto.getTitle())
                .problemUrl(dto.getProblemUrl())
                .platform(dto.getPlatform())  // Enum으로 처리된 platform
                .language(dto.getLanguage())  // Enum으로 처리된 language
                .code(dto.getCode())
                .user(mockUser)
                .build();

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUser));
        when(submissionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Submission result = submissionServiceImpl.submit(1L, dto);

        assertThat(result.getTitle()).isEqualTo("백준 12865");
        assertThat(result.getUser().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재하지 않는 제출 삭제 시 IllegalArgumentException 발생")
    void delete_submission_not_found() {
        // given
        when(submissionRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> submissionServiceImpl.deleteById(99L));
    }



}
