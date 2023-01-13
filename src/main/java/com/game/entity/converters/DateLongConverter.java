package com.game.entity.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Converter
public class DateLongConverter implements AttributeConverter<Long, String> {
    private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    @Override
    public Long convertToEntityAttribute(final String attribute) {
        try {
            return DATE_FORMAT_THREAD_LOCAL.get().parse(attribute).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToDatabaseColumn(final Long dbData) {
        return DATE_FORMAT_THREAD_LOCAL.get().format(new Date(dbData));
    }
}
