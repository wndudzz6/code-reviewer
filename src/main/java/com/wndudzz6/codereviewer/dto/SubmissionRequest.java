package com.wndudzz6.codereviewer.dto;


import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionRequest {
    private String title;
    private String problemUrl;
    private String language;
    private String code;
}
