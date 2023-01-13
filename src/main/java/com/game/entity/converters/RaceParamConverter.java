package com.game.entity.converters;

import com.game.entity.Race;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RaceParamConverter implements Converter<String, Race> {
    @Override
    public Race convert(final String source) {
        return Race.valueOf(source);
    }
}
