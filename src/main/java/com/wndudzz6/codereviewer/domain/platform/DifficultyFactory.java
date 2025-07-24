package com.wndudzz6.codereviewer.domain.platform;

public class DifficultyFactory {
    public static Difficulty from(String platform, String difficultyName) {
        String normalized = normalizeEnumName(difficultyName);

        return switch (platform.toUpperCase()) {
            case "BOJ" -> BojDifficulty.valueOf(normalized);
            case "PROGRAMMERS" -> ProgrammersDifficulty.valueOf(normalized);
            default -> throw new IllegalArgumentException("지원하지 않는 플랫폼입니다: " + platform);
        };
    }

    private static String normalizeEnumName(String input) {
        return input.trim().toUpperCase().replace("-", "_").replace(" ", "_");
    }
}
