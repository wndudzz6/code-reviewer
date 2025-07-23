package com.wndudzz6.codereviewer.dto;


import com.wndudzz6.codereviewer.domain.Language;
import com.wndudzz6.codereviewer.domain.platform.Platform;
import lombok.*;

@Getter @Setter
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 필드를 매개변수로 받는 생성자
@Builder //선택적으로 값을 넣는 생성자
public class SubmissionRequest {
    private String title;
    private String problemUrl;
    private Platform platform;
    private Language language;
    private String code;
}
