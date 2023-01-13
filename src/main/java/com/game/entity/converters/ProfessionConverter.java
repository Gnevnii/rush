package com.game.entity.converters;

import com.game.entity.Profession;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Converter
public class ProfessionConverter implements AttributeConverter<Profession, String> {
    @Override
    public String convertToDatabaseColumn(final Profession attribute) {
        return attribute.name();
    }

    @Override
    public Profession convertToEntityAttribute(final String dbData) {
        return Profession.valueOf(dbData);
    }
}
