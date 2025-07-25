package com.wndudzz6.codereviewer.domain.platform;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DifficultyConverter implements AttributeConverter<Difficulty, String> {

    @Override
    public String convertToDatabaseColumn(Difficulty difficulty) {
        return difficulty == null ? null : difficulty.name();
    }

    @Override
    public Difficulty convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return Difficulty.from(dbData);
    }
}
