package com.wndudzz6.codereviewer.dto;

import com.wndudzz6.codereviewer.domain.platform.Difficulty;
import com.wndudzz6.codereviewer.domain.platform.DifficultyConverter;
import jakarta.persistence.Convert;
import lombok.*;
import java.util.List;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//GPT응답을 ReviewRequest로 변환
public class ReviewRequest {
    private String summary;
    private String strategy;
    private String codeQuality;
    private String improvement;
    private String timeComplexity;
    private String difficulty;
    private String platform;

    private List<String> tags;
}
