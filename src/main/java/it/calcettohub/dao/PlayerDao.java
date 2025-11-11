package it.calcettohub.dao;

import it.calcettohub.model.Player;

import java.util.Optional;

public interface PlayerDao {
    void add(Player player);
    void delete(String email);
    Optional<Player> findByEmail(String email);
}