package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.Dao;
import com.game.service.CreateService;
import com.game.service.PlayerUtils;
import com.game.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private final Dao dao;
    private final CreateService createService;
    private final UpdateService updateService;
    private final PlayerUtils utils;

    @Autowired
    public Controller(final Dao dao,
                      final CreateService createService,
                      final UpdateService updateService,
                      final PlayerUtils utils) {
        this.dao = dao;
        this.createService = createService;
        this.updateService = updateService;
        this.utils = utils;
    }

    @RequestMapping(value = "/rest/players", method = RequestMethod.GET)
    public @ResponseBody List<Player> getPlayers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel,
            @RequestParam(required = false) PlayerOrder order,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
        return dao.getPlayers(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);
    }

    @RequestMapping(value = "/rest/players/count", method = RequestMethod.GET)
    public @ResponseBody Long getPlayersCount(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel) {
        return dao.getPlayersCount(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
    }

    @RequestMapping(value = "/rest/players", method = RequestMethod.POST)
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        if (createService.getValidator().isInvalid(player)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final Player created = createService.create(player);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/players/{id}", method = RequestMethod.POST)
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player, @PathVariable String id) {
        if (updateService.getValidator().isInvalid(player, id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final Player dbPlayer = dao.get(Long.valueOf(id));
        if (dbPlayer == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        final Player updated = updateService.update(dbPlayer, player);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/players/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Player> deletePlayer(@PathVariable String id) {
        if (utils.isInvalid(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final Player dbPlayer = dao.get(Long.valueOf(id));
        if (dbPlayer == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        dao.delete(Long.valueOf(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/players/{id}", method = RequestMethod.GET)
    public ResponseEntity<Player> getPlayer(@PathVariable final String id) {
        if (utils.isInvalid(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final Player player = dao.get(Long.valueOf(id));
        if (player == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }
}
