package it.calcettohub.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidationUtils {
    private ValidationUtils() {}

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");
    private static final Pattern NAME_SURNAME_PATTERN = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ'\\-\\s]+$");

    public static boolean isValidEmail(String email) {
        return isNotNull(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return isNotNull(password) && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean passwordMatch(String password, String confirmPassword) {
        return isNotEmpty(password) && isNotEmpty(confirmPassword) && password.equals(confirmPassword);
    }

    public static boolean isValidName(String string) {
        return isNotEmpty(string) && NAME_SURNAME_PATTERN.matcher(string).matches();
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isValidAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) return false;
        LocalDate today = LocalDate.now();

        if (dateOfBirth.isAfter(today)) {
            return false;
        }

        LocalDate minAllowedDate = today.minusYears(14);
        return dateOfBirth.isBefore(minAllowedDate) || dateOfBirth.isEqual(minAllowedDate);
    }
}
