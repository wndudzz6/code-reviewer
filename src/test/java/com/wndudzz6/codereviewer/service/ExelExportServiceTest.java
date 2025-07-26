package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.dto.ReviewSubmissionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExelExportServiceTest {

    private ExelExportService exelExportService;

    @BeforeEach
    void setUp() {
        exelExportService = new ExelExportService();
    }

    @Test
    void testExportDataToStream() throws Exception {
        // Given
        ReviewSubmissionDTO dto1 = new ReviewSubmissionDTO(
                1L, "Title 1", "http://example.com", "Code 1", "Java", "Platform 1",
                "Summary 1", "Strategy 1", "Code Quality 1", "Improvement 1",
                "O(n)", "Medium", Arrays.asList("Tag 1", "Tag 2"), LocalDateTime.parse("2025-07-26T00:00:00")
        );
        List<ReviewSubmissionDTO> data = List.of(dto1);

        // When
        ByteArrayOutputStream out = exelExportService.exportDataToStream(data);

        // Then
        assertNotNull(out);
        assertTrue(out.size() > 0); // 바이트 스트림이 비어 있지 않은지 확인
    }
}
