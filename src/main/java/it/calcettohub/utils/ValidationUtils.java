package it.calcettohub.utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidationUtils {
    private ValidationUtils() {}

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+39)?\\s?(\\d\\s?){9,10}$");

    public static boolean isValidEmail(String email) {
        return isNotNull(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return isNotNull(password) && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean passwordMatch(String password, String confirmPassword) {
        return isNotEmpty(password) && isNotEmpty(confirmPassword) && password.equals(confirmPassword);
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isValidDate(LocalDate dateOfBirth) {
        return dateOfBirth.isBefore(LocalDate.now());
    }

    public static boolean isValidPhone(String phoneNumber) {
        return isNotNull(phoneNumber) && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    public static boolean isValidAddress(String address) {
        if (!isNotEmpty(address)) return false;

        String s = address.trim();
        if (s.length() > 200) return false;

        int lastSpace = s.lastIndexOf(' ');
        if (lastSpace <= 0 || lastSpace == s.length() - 1) return false;

        String street = s.substring(0, lastSpace).trim();
        String civic = s.substring(lastSpace + 1).trim();

        if (street.endsWith(",")) {
            street = street.substring(0, street.length() - 1).trim();
        }

        if (street.isEmpty() || civic.isEmpty()) return false;

        boolean hasLetter = false;

        for (int i = 0; i < street.length(); i++) {
            char c = street.charAt(i);

            if (!(c == ' ' || c == '.' || c == '\'' || c == '-')) {
                if (Character.isLetter(c)) {
                    hasLetter = true;
                } else {
                    return false;
                }
            }
        }

        if (!hasLetter) return false;



        return isValidCivic(civic);
    }

    private static boolean isValidCivic(String civic) {
        int n = civic.length();
        int i = 0;

        while (i < n && Character.isDigit(civic.charAt(i))) i++;
        if (i == 0) return false;

        if (i == n) return true;

        char c = civic.charAt(i);

        if (Character.isLetter(c)) {
            i++;
            return i == n;
        }

        if (c == '/') {
            i++;
            if (i >= n) return false;
            if (!Character.isLetter(civic.charAt(i))) return false;
            i++;
            return i == n;
        }

        return false;
    }

    public static boolean isPastBookingDate(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}