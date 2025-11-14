package it.calcettohub.dao;

import it.calcettohub.model.Player;
import it.calcettohub.util.DemoRepository;
import it.calcettohub.util.PasswordUtils;

import java.util.Map;
import java.util.Optional;

public class PlayerDemoDao implements PlayerDao {
    private static PlayerDemoDao instance;
    private final Map<String, Player> players;

    public PlayerDemoDao() {
        this.players = DemoRepository.getInstance().getPlayers();
    }

    public static synchronized PlayerDemoDao getInstance() {
        if (instance == null) {
            instance = new PlayerDemoDao();
        }
        return instance;
    }

    @Override
    public void add(Player player) {
        String email = player.getEmail();
        if (players.containsKey(email)) {
            return;
        }

        String hashedPassword = PasswordUtils.hashPassword(player.getPassword());
        player.setPassword(hashedPassword);

        players.put(email,player);
    }

    @Override
    public void update(Player player) {
        String email = player.getEmail();
        Player existingPlayer = players.get(email);

        if (existingPlayer == null) {
            return;
        }

        // conserva la password hashata
        player.setPassword(existingPlayer.getPassword());

        players.put(email, player);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        Player player = players.get(email);

        if (player == null) {
            return;
        }

        String newHashedPassword = PasswordUtils.hashPassword(newPassword);
        player.setPassword(newHashedPassword);
    }

    @Override
    public void delete(String email) {
        if (!players.containsKey(email)) {
            return;
        }

        players.remove(email);
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        return Optional.ofNullable(players.get(email));
    }
}
