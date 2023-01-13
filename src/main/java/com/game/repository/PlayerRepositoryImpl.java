package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepositoryImpl extends CrudRepository<Player, Long> {
    Player findPlayerById(Long id);
}
