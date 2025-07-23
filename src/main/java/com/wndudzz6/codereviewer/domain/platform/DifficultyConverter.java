package com.wndudzz6.codereviewer.domain.platform;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;

@Converter(autoApply = false)
public class DifficultyConverter implements AttributeConverter<Difficulty, String> {

    @Override
    public String convertToDatabaseColumn(Difficulty difficulty) {
        return difficulty == null ? null :difficulty.name();
    }

    @Override
    public Difficulty convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        //BojDifficulty
        for(BojDifficulty d : BojDifficulty.values()){
            if(d.name().equals(dbData)) return d;
        }

        //ProgrammersDifficulty
        for(ProgrammersDifficulty p : ProgrammersDifficulty.values()){
            if(p.name().equals(dbData)) return p;
        }

        throw new IllegalArgumentException("Unknown Difficulty: "+dbData);
    }
}
