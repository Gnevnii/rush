package com.game.entity.converters;

import com.game.entity.Profession;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfessionParamConverter implements Converter<String, Profession> {
    @Override
    public Profession convert(final String source) {
        return Profession.valueOf(source);
    }
}
