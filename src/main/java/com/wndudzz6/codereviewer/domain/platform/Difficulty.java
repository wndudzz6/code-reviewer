package com.wndudzz6.codereviewer.domain.platform;


public enum Difficulty {
    // BOJ
    BRONZE_5("BOJ"),
    BRONZE_4("BOJ"),
    BRONZE_3("BOJ"),
    BRONZE_2("BOJ"),
    BRONZE_1("BOJ"),

    SILVER_5("BOJ"),
    SILVER_4("BOJ"),
    SILVER_3("BOJ"),
    SILVER_2("BOJ"),
    SILVER_1("BOJ"),

    GOLD_5("BOJ"),
    GOLD_4("BOJ"),
    GOLD_3("BOJ"),
    GOLD_2("BOJ"),
    GOLD_1("BOJ"),

    PLATINUM_5("BOJ"),
    PLATINUM_4("BOJ"),
    PLATINUM_3("BOJ"),
    PLATINUM_2("BOJ"),
    PLATINUM_1("BOJ"),

    DIAMOND_5("BOJ"),
    DIAMOND_4("BOJ"),
    DIAMOND_3("BOJ"),
    DIAMOND_2("BOJ"),
    DIAMOND_1("BOJ"),

    RUBY_5("BOJ"),
    RUBY_4("BOJ"),
    RUBY("BOJ"),

    // PROGRAMMERS
    LEVEL_1("PROGRAMMERS"),
    LEVEL_2("PROGRAMMERS"),
    LEVEL_3("PROGRAMMERS"),
    LEVEL_4("PROGRAMMERS"),
    LEVEL_5("PROGRAMMERS");

    private final String platform;

    Difficulty(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public static Difficulty from(String name) {
        return Difficulty.valueOf(normalize(name));
    }

    private static String normalize(String input) {
        return input.trim().toUpperCase().replace("-", "_").replace(" ", "_");
    }
}