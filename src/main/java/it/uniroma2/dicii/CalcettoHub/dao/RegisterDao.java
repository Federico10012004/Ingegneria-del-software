package it.uniroma2.dicii.CalcettoHub.dao;

import java.time.LocalDate;

public class RegisterDao {

    public static final String CorrectEmail = "admin@mail.com";

    public int checkField(String user, LocalDate date, String email, String password, String checkPassword) {

        if (user == null || user.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty() || checkPassword == null || checkPassword.isEmpty() || date == null) {
            return 1;
        } else if (!password.equals(checkPassword)) {
            return -1;
        } else if (email.equals(CorrectEmail)){
            return -2;
        } else {
            return 0;
        }
    }
}
