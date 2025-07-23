package com.wndudzz6.codereviewer.domain.platform;

public enum ProgrammersDifficulty implements Difficulty {
    LEVEL_1,
    LEVEL_2,
    LEVEL_3,
    LEVEL_4,
    LEVEL_5;

    @Override
    public String getPlatform() {
        return "Programmers";
    }
}
