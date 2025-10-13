package main.java.it.uniroma2.dicii.CalcettoHub.dao;

public class LoginDao {
    public String correctEmail = "admin@mail.com";
    public String correctPassword = "admin";

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
