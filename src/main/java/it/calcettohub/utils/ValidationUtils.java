package it.calcettohub.utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidationUtils {
    private ValidationUtils() {}

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+39)?\\s?(\\d\\s?){9,10}$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^(?>[\\p{L}.'-]+(?:\\s+[\\p{L}.'-]+)*)\\s*,?\\s*\\d+(?:\\s*/\\s*\\p{L})?$");

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
        return isNotNull(address) && ADDRESS_PATTERN.matcher(address).matches();
    }

    public static boolean isPastBookingDate(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}
