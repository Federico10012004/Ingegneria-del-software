package it.calcettohub.dao;

import it.calcettohub.model.Player;

import java.util.List;

public interface PlayerDao {
    void add(Player player);
    void delete(String email);
    List<Player> findAll();
    Player findByEmail(String email);
}