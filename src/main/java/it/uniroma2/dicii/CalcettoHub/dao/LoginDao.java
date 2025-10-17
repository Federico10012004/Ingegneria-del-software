package it.uniroma2.dicii.CalcettoHub.dao;

public class LoginDao {
    public static final String correctEmail = "admin@mail.com";
    public static final String correctPassword = "admin";

    public int checkCredentials(String email, String password) {

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return -1;
        } else if (!email.equals(correctEmail) || !password.equals(correctPassword)) {
            return 1;
        } else {
            return 0;
        }
    }
}
