package it.calcettohub.dao;

public class LoginDao {
    public static final String CORRECT_EMAIL = "admin@mail.com";
    public static final String CORRECT_PASSWORD = "admin";

    public int checkCredentials(String email, String password) {

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return -1;
        } else if (!email.equals(CORRECT_EMAIL) || !password.equals(CORRECT_PASSWORD)) {
            return 1;
        } else {
            return 0;
        }
    }
}
