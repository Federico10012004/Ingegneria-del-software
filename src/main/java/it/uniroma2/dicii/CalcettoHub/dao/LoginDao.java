package it.uniroma2.dicii.CalcettoHub.dao;

public class LoginDao {
    public static final String Correct_Email = "admin@mail.com";
    public static final String Correct_Password = "admin";

    public int checkCredentials(String email, String password) {

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return -1;
        } else if (!email.equals(Correct_Email) || !password.equals(Correct_Password)) {
            return 1;
        } else {
            return 0;
        }
    }
}
