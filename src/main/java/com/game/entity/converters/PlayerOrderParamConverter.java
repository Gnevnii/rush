package com.game.entity.converters;

import com.game.controller.PlayerOrder;
import com.game.entity.Race;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PlayerOrderParamConverter implements Converter<String, PlayerOrder> {
    @Override
    public PlayerOrder convert(final String source) {
        return PlayerOrder.valueOf(source);
    }
}
