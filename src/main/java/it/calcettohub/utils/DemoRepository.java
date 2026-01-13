package it.calcettohub.utils;

import it.calcettohub.model.Booking;
import it.calcettohub.model.Field;
import it.calcettohub.model.FieldManager;
import it.calcettohub.model.Player;
import it.calcettohub.model.valueobject.Notification;

import java.util.HashMap;
import java.util.Map;

public class DemoRepository {

    private static DemoRepository instance;
    private final Map<String, Player> players;
    private final Map<String, FieldManager> fieldManagers;
    private final Map<String, Field> fields;
    private final Map<String, Booking> bookings;
    private final Map<String, Notification> notifications;

    private DemoRepository() {
        this.players = new HashMap<>();
        this.fieldManagers = new HashMap<>();
        this.fields = new HashMap<>();
        this.bookings = new HashMap<>();
        this.notifications = new HashMap<>();
    }

    public static synchronized DemoRepository getInstance() {
        if (instance == null) {
            instance = new DemoRepository();
        }
        return instance;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public Map<String, FieldManager> getFieldManagers() {
        return fieldManagers;
    }

    public Map<String, Field> getFields() {
        return fields;
    }

    public Map<String, Booking> getBookings() {
        return bookings;
    }

    public Map<String, Notification> getNotifications() {
        return notifications;
    }
}
