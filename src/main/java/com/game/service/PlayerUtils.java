package com.game.service;

import com.game.entity.Player;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class PlayerUtils {

    public boolean isInvalid(final String id) {
        if (id == null || id.isEmpty()) return true;

        if (!Pattern.compile("\\d+").matcher(id).matches()) {
            return true;
        }

        final long value = Long.parseLong(id);
        return value <= 0;
    }

    private int calculateLevel(Player player) {
        return (int) ((Math.sqrt(2500 + (200 * player.experience)) - 50) / 100);
    }

    private int calculateUntilNextLevel(Player player) {
        return 50 * (player.level + 1) * (player.level + 2) - player.experience;
    }

    public void updateNewPlayerAttr(Player player) {
        player.setLevel(calculateLevel(player));
        player.setUntilNextLevel(calculateUntilNextLevel(player));
    }
}
