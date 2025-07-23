package com.wndudzz6.codereviewer.domain.platform;

public enum Platform {
    BOJ("백준"),
    PROGRAMMERS("프로그래머스");

    private String label;
    Platform(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
