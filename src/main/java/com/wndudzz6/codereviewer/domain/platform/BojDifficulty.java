package com.wndudzz6.codereviewer.domain.platform;

public enum BojDifficulty implements Difficulty{
    BRONZE_5,
    BRONZE_4,
    BRONZE_3,
    BRONZE_2,
    BRONZE_1,

    SILVER_5,
    SILVER_4,
    SILVER_3,
    SILVER_2,
    SILVER_1,

    GOLD_5,
    GOLD_4,
    GOLD_3,
    GOLD_2,
    GOLD_1,

    PLATINUM_5,
    PLATINUM_4,
    PLATINUM_3,
    PLATINUM_2,
    PLATINUM_1,

    DIAMOND_5,
    DIAMOND_4,
    DIAMOND_3,
    DIAMOND_2,
    DIAMOND_1,

    RUBY_5,
    RUBY_4,
    RUBY_3,
    RUBY_2,
    RUBY_1;

    @Override
    public String getPlatform() {
        return "BOJ";
    }
}
