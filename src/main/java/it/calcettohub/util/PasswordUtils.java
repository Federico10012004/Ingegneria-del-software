package it.calcettohub.util;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class PasswordUtils {
    @FXML
    public static boolean togglePasswordVisibility(PasswordField passwordField, TextField textField, boolean isVisible) {
        if (isVisible) {
            passwordField.setText(textField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            textField.setVisible(false);
            textField.setManaged(false);
        } else {
            textField.setText(passwordField.getText());
            textField.setVisible(true);
            textField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
        }
        return !isVisible;
    }

    public static void updateEyeIcon(ImageView eyeIcon, boolean isVisible) {
        String imagePath = isVisible ? "/img/eye.png" : "/img/hidden.png";
        eyeIcon.setImage(new Image(Objects.requireNonNull(PasswordUtils.class.getResourceAsStream(imagePath))));
    }

    public static void bindPasswordFields(PasswordField passwordField, TextField textField, boolean isVisible) {
        textField.setVisible(isVisible);
        textField.setManaged(isVisible);

        passwordField.textProperty().addListener((_, _, newV) -> {
            if (!isVisible) textField.setText(newV);
        });
        textField.textProperty().addListener((_, _, newV) -> {
            if (isVisible) passwordField.setText(newV);
        });
    }

    private PasswordUtils() {
        throw new UnsupportedOperationException("Classe di utilità, non deve essere istanziata");
    }

    // Crea hash della password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); // 12 = "cost factor"
    }

    // Verifica se la password è corretta
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
