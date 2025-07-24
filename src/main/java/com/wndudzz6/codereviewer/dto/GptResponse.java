package com.wndudzz6.codereviewer.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GptResponse {

    private List<GptChoice> choices;

    @Getter @Setter
    public static class GptChoice {
        private GptMessage message;
    }

    @Getter @Setter
    public static class GptMessage {
        private String role;
        private String content;
    }


}
