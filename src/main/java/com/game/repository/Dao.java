package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class Dao {
    @PersistenceContext
    private EntityManager entityManager;
    private final PlayerRepositoryImpl repository;

    @Autowired
    public Dao(final PlayerRepositoryImpl repository) {
        this.repository = repository;
    }

    public List<Player> getPlayers(final String name,
                                   final String title,
                                   final Race race,
                                   final Profession profession,
                                   final Long after,
                                   final Long before,
                                   final Boolean banned,
                                   final Integer minExperience,
                                   final Integer maxExperience,
                                   final Integer minLevel,
                                   final Integer maxLevel,
                                   final PlayerOrder order,
                                   final Integer pageNumber,
                                   final Integer pageSize) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        final CriteriaQuery<Player> query = cb.createQuery(Player.class);
        final Root<Player> root = query.from(Player.class);

        final ArrayList<Predicate> predicates = getPredicates(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, cb, root);

        query.select(root);
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        cb.desc(order != null
                ? root.get(order.getFieldName())
                : root.get(PlayerOrder.ID.getFieldName()));

        int pgSize = pageSize == null ? 3 : pageSize;
        int pgNum = pageNumber == null ? 0 : pageNumber * pgSize;

        TypedQuery<Player> typedQuery = entityManager
                .createQuery(query)
                .setFirstResult(pgNum)
                .setMaxResults(pgSize);
        return typedQuery.getResultList();
    }

    private static ArrayList<Predicate> getPredicates(final String name, final String title, final Race race, final Profession profession, final Long after, final Long before, final Boolean banned, final Integer minExperience, final Integer maxExperience, final Integer minLevel, final Integer maxLevel, final CriteriaBuilder cb, final Root<Player> root) {
        final ArrayList<Predicate> predicates = new ArrayList<>();
        if (name != null && !name.isEmpty())
            predicates.add(cb.like(root.get("name"), "%" + name.toLowerCase() + "%"));

        if (title != null && !title.isEmpty())
            predicates.add(cb.like(root.get("title"), "%" + title.toLowerCase() + "%"));

        if (race != null)
            predicates.add(cb.equal(root.get("race"), race));

        if (profession != null)
            predicates.add(cb.equal(root.get("profession"), profession));

        if (after != null && before != null) {
            predicates.add(cb.between(root.get("birthday"), after, before));
        } else if (after != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("birthday"), after));
        } else if (before != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("birthday"), before));
        }

        if (banned != null)
            predicates.add(banned ? cb.isTrue(root.get("banned")) : cb.isFalse(root.get("banned")));

        if (maxExperience != null && minExperience != null) {
            predicates.add(cb.between(root.get("experience"), minExperience, maxExperience));
        } else if (maxExperience != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("experience"), maxExperience));
        } else if (minExperience != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("experience"), minExperience));
        }

        if (minLevel != null && maxLevel != null) {
            predicates.add(cb.between(root.get("level"), minLevel, maxLevel));
        } else if (maxLevel != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("level"), maxLevel));
        } else if (minLevel != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("level"), minLevel));
        }
        return predicates;
    }

    public Long getPlayersCount(final String name,
                                final String title,
                                final Race race,
                                final Profession profession,
                                final Long after,
                                final Long before,
                                final Boolean banned,
                                final Integer minExperience,
                                final Integer maxExperience,
                                final Integer minLevel,
                                final Integer maxLevel) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        final CriteriaQuery<Long> query = cb.createQuery(Long.class);
        final Root<Player> root = query.from(Player.class);

        final ArrayList<Predicate> predicates = getPredicates(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, cb, root);

        query
                .select(cb.count(root))
                .where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        System.out.println(typedQuery.unwrap(Query.class).getQueryString());
        return typedQuery.getSingleResult();
    }

    @Transactional
    public Player save(final Player player) {
        return repository.save(player);
    }

    public Player get(final Long id) {
        return repository.findPlayerById(id);
    }

    @Transactional
    public void delete(final Long id) {
        repository.deleteById(id);
    }
}
