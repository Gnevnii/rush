package com.game.entity;

import com.game.entity.converters.DateLongConverter;
import com.game.entity.converters.ProfessionConverter;
import com.game.entity.converters.RaceConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String title;

    @Convert(converter = RaceConverter.class)
    public Race race;

    @Convert(converter = ProfessionConverter.class)
    public Profession profession;

    @Convert(converter = DateLongConverter.class)
    public Long birthday;

    public Boolean banned;
    public Integer experience;
    public Integer level;
    public Integer untilNextLevel;

    public Player() {
    }

    public Player(Long id, String name, String title, Race race, Profession profession, Long birthday,
                  Boolean banned, Integer experience, Integer level, Integer untilNextLevel) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.banned = banned;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(final Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(final Profession profession) {
        this.profession = profession;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(final Long birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(final Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(final Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(final Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(final Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player that = (Player) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(title, that.title) &&
                race == that.race &&
                profession == that.profession &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(banned, that.banned) &&
                Objects.equals(experience, that.experience) &&
                Objects.equals(level, that.level) &&
                Objects.equals(untilNextLevel, that.untilNextLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, title, race, profession, birthday, banned, experience, level, untilNextLevel);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                '}';
    }
}