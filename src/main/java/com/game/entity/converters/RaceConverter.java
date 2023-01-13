package com.game.entity.converters;

import com.game.entity.Race;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RaceConverter implements AttributeConverter<Race, String> {
    @Override
    public String convertToDatabaseColumn(final Race attribute) {
        return attribute.name();
    }

    @Override
    public Race convertToEntityAttribute(final String dbData) {
        return Race.valueOf(dbData);
    }
}
