package it.calcettohub.dao;

import it.calcettohub.model.Player;

public interface PlayerDao {
    void add(Player player);
    void delete(String email);
    Player findByEmail(String email);
}