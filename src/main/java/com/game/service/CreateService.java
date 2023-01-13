package com.game.service;

import com.game.entity.Player;
import com.game.repository.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class CreateService {
    private final Dao dao;
    private final PlayerUtils utils;

    @Autowired
    public CreateService(final Dao dao,
                         final PlayerUtils utils) {
        this.dao = dao;
        this.utils = utils;
    }

    @Transactional
    public Player create(Player player) {
        utils.updateNewPlayerAttr(player);

        if (player.banned == null) player.banned = false;
        return dao.save(player);
    }

    public CreateValidation getValidator() {
        return new CreateValidation();
    }


    public static class CreateValidation {
        public boolean isInvalid(final Player player) {
            if (player.name == null ||
                    player.title == null ||
                    player.race == null ||
                    player.profession == null ||
                    player.birthday == null ||
                    player.experience == null)
                return true;

            if (player.name.isEmpty()
                    || player.name.length() > 12
                    || player.title.length() > 30)
                return true;

            if (isInvalidExperienceForCreate(player)) return true;
            return isInvalidBirthDateForCreate(player);
        }

        boolean isInvalidExperienceForCreate(final Player player) {
            return player.experience < 0
                    || player.experience > 10_000_000;
        }

        boolean isInvalidBirthDateForCreate(final Player player) {
            if (player.birthday < 0) return true;

            return player.birthday < Constant.YEAR_2000
                    || player.birthday > Constant.YEAR_3000;
        }
    }
}
