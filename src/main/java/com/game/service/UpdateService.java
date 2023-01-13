package com.game.service;

import com.game.entity.Player;
import com.game.repository.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class UpdateService {
    private final PlayerUtils utils;
    private final Dao dao;

    @Autowired
    public UpdateService(final PlayerUtils utils,
                         final Dao dao) {
        this.utils = utils;
        this.dao = dao;
    }

    @Transactional
    public Player update(Player existing, Player updated) {
        updateNonNullAttrs(updated, existing);
        updateNewPlayerData(existing);
        return dao.save(existing);
    }

    void updateNewPlayerData(final Player player) {
        utils.updateNewPlayerAttr(player);
    }

    void updateNonNullAttrs(final Player player, final Player dbPlayer) {
        if (player.name != null)
            dbPlayer.name = player.name;

        if (player.title != null)
            dbPlayer.title = player.title;

        if (player.race != null)
            dbPlayer.race = player.race;

        if (player.profession != null)
            dbPlayer.profession = player.profession;

        if (player.birthday != null)
            dbPlayer.birthday = player.birthday;

        if (player.banned != null)
            dbPlayer.banned = player.banned;

        if (player.experience != null)
            dbPlayer.experience = player.experience;
    }

    public UpdateValidator getValidator() {
        return new UpdateValidator(utils);
    }


    public static class UpdateValidator {
        private final PlayerUtils utils;

        public UpdateValidator(final PlayerUtils utils) {
            this.utils = utils;
        }

        public boolean isInvalid(final Player player, final String id) {
            if (isEmpty(player)) return false;

            return utils.isInvalid(id)
                    || isInvalidBirthDateForUpdate(player)
                    || isInvalidExperienceForUpdate(player);
        }

        boolean isEmpty(final Player player) {
            return player.name == null
                    && player.title == null
                    && player.race == null
                    && player.profession == null
                    && player.birthday == null
                    && player.banned == null
                    && player.experience == null;
        }

        boolean isInvalidBirthDateForUpdate(final Player player) {
            if (player.birthday == null) return false;

            if (player.birthday < 0) return true;
            return player.birthday < Constant.YEAR_2000
                    || player.birthday > Constant.YEAR_3000;
        }

        boolean isInvalidExperienceForUpdate(final Player player) {
            if (player.experience == null) return false;

            return player.experience < 0
                    || player.experience > 10_000_000;
        }
    }
}
