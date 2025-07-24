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
    private Long userId;
    private String problemUrl;
    private Platform platform;
    private Language language;
    private String code;

//    // platform과 language를 객체로 변환하는 메서드 추가
    public Platform getPlatformEnum() {
        return Platform.valueOf(platform.name());    // 문자열을 Platform 객체로 변환
    }

    public Language getLanguageEnum() {
        return Language.valueOf(language.name());  // String을 Language Enum으로 변환
    }

//    public Language getLanguageEnum() {
//        if (language.equalsIgnoreCase("C++")) {
//            return Language.C_PLUS_PLUS;  // C++를 C_PLUS_PLUS로 변환
//        }
//        try {
//            return Language.valueOf(language.toUpperCase());  // 대소문자 처리
//        } catch (IllegalArgumentException e) {
//            return null;  // 유효하지 않은 언어 값에 대해서는 null 반환
//        }
//    }


}
