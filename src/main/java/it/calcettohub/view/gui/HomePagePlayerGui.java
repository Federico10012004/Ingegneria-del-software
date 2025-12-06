package it.calcettohub.view.gui;

import it.calcettohub.bean.PlayerAccountBean;
import it.calcettohub.controller.AccountController;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.PasswordUtils;
import it.calcettohub.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeParseException;

public class HomePagePlayerGui extends BaseFormerGui {
    @FXML private ImageView eyeIcon;
    @FXML private ImageView eyeConfirmIcon;
    @FXML private ImageView fieldIcon;
    @FXML private ImageView matchIcon;
    @FXML private ImageView accountIcon;
    @FXML private ImageView backIcon;
    @FXML private ImageView backIcon1;
    @FXML private Label errorLabel;
    @FXML private Label errorLabel1;
    @FXML private Label accountLabel;
    @FXML private Label passwordLabel;
    @FXML private Label helloLabel;
    @FXML private Label fieldLabel;
    @FXML private Label matchLabel;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField positionField;
    @FXML private TextField passwordTextField;
    @FXML private TextField confirmPasswordTextField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private DatePicker dateOfBirthField;
    @FXML private Button confirmModifyButton;
    @FXML private VBox accountPanel;
    @FXML private VBox changePasswordPanel;
    @FXML private VBox organizeMatch;
    @FXML private HBox buttonBox;

    private boolean isVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private Player currentPlayer;
    private PlayerAccountBean initialState;
    private final AccountController controller = new AccountController();

    @FXML
    private void initialize() {
        PasswordUtils.bindPasswordFields(passwordField, passwordTextField, isVisible);
        PasswordUtils.bindPasswordFields(confirmPasswordField, confirmPasswordTextField, isConfirmPasswordVisible);

        setEyeIcon();
        setNodeVisibility(confirmModifyButton, false);
        setNodeVisibility(errorLabel, false);
        setNodeVisibility(errorLabel1, false);

        bindResponsiveLogo(logoGroup, 1000.0);
        setupResponsiveLabel(fieldLabel, root, 30.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(matchLabel, root, 30.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(accountLabel, root, 35.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(passwordLabel, root, 35.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(helloLabel, root, 45.0, FONT_STYLE_TAHOMA);

        setUpResponsiveNode(accountPanel, root, 0.40);
        setUpResponsiveNode(changePasswordPanel, root, 0.40);

        setUpResponsiveIcon(fieldIcon, root, 0.075);
        setUpResponsiveIcon(matchIcon, root, 0.075);
        setUpResponsiveIcon(backIcon, root, 0.02);
        setUpResponsiveIcon(backIcon1, root, 0.02);
        setUpResponsiveIcon(accountIcon, root, 0.03);

        currentPlayer = (Player) SessionManager.getInstance().getLoggedUser();
        helloLabel.setText("Ciao " + currentPlayer.getEmail());
        populateFields();
        setupAccountChangeListeners();
    }

    private void populateFields() {
        nameField.setText(currentPlayer.getName());
        surnameField.setText(currentPlayer.getSurname());
        dateOfBirthField.setValue(currentPlayer.getDateOfBirth());
        positionField.setText(currentPlayer.getPreferredPosition().toString());

        initialState = new PlayerAccountBean();
        initialState.setName(currentPlayer.getName());
        initialState.setSurname(currentPlayer.getSurname());
        initialState.setDateOfBirth(currentPlayer.getDateOfBirth());
        initialState.setPosition(currentPlayer.getPreferredPosition());
        initialState.setPassword(currentPlayer.getPassword());
    }

    private void setupAccountChangeListeners() {
        // usa lo stesso listener per tutti i campi
        javafx.beans.value.ChangeListener<Object> listener = (_, _, _) -> updateConfirmButtonVisibility();

        nameField.textProperty().addListener(listener);
        surnameField.textProperty().addListener(listener);
        positionField.textProperty().addListener(listener);
        dateOfBirthField.valueProperty().addListener(listener);
    }

    private void updateConfirmButtonVisibility() {
        boolean changed = hasAccountChanges();
        setNodeVisibility(confirmModifyButton, changed);
    }

    @FXML
    private void showAccount() {
        setNodeVisibility(accountPanel, true);
        setNodeVisibility(changePasswordPanel, false);
        organizeMatch.setVisible(false);
        buttonBox.setMouseTransparent(true);
    }

    @FXML
    private void showChangePassword() {
        setNodeVisibility(changePasswordPanel, true);
        setNodeVisibility(accountPanel, false);
    }

    @FXML
    private void togglePasswordVisibility(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        ImageView clickedEye = (ImageView) clickedButton.getGraphic();

        if (clickedEye == eyeIcon) {
            isVisible = PasswordUtils.togglePasswordVisibility(passwordField, passwordTextField, isVisible);
        } else if (clickedEye == eyeConfirmIcon) {
            isConfirmPasswordVisible = PasswordUtils.togglePasswordVisibility(confirmPasswordField, confirmPasswordTextField, isConfirmPasswordVisible);
        }
        setEyeIcon();
    }

    private void setEyeIcon() {
        PasswordUtils.updateEyeIcon(eyeIcon, isVisible);
        PasswordUtils.updateEyeIcon(eyeConfirmIcon, isConfirmPasswordVisible);
    }

    @FXML
    private void changePassword() {
        PlayerAccountBean bean = new PlayerAccountBean();

        try {
            validateField(()-> bean.setPassword(isVisible ? passwordTextField.getText().trim() : passwordField.getText().trim()));
            validateField(()-> bean.setConfirmPassword(isConfirmPasswordVisible ? confirmPasswordTextField.getText().trim() : confirmPasswordField.getText().trim()));

            controller.updateUserPassword(bean);

            showInfo("Password modificata con successo.");

            reset();
            setNodeVisibility(accountPanel, true);
            setNodeVisibility(changePasswordPanel, false);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            setErrorMessage(errorLabel1, e.getMessage());
            showError(errorLabel1);
        }
    }

    @FXML
    private void modifyAccount() {
        PlayerAccountBean bean = new PlayerAccountBean();

        if (!hasAccountChanges()) {
            return;
        }

        try {
            validateField(()-> bean.setName(nameField.getText().trim()));
            validateField(()-> bean.setSurname(surnameField.getText().trim()));
            validateField(()-> bean.setDateOfBirth(dateOfBirthField.getValue()));
            validateField(()-> bean.setPosition(PlayerPosition.fromString(positionField.getText().trim())));

            controller.updateUserData(bean);

            currentPlayer.setName(bean.getName());
            currentPlayer.setSurname(bean.getSurname());
            currentPlayer.setDateOfBirth(bean.getDateOfBirth());
            currentPlayer.setPreferredPosition(bean.getPosition());

            initialState.setName(bean.getName());
            initialState.setSurname(bean.getSurname());
            initialState.setDateOfBirth(bean.getDateOfBirth());
            initialState.setPosition(bean.getPosition());

            showInfo("Modifiche applicate con successo.");
            setNodeVisibility(confirmModifyButton, false);
            populateFields();
        } catch (IllegalArgumentException | DateTimeParseException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showError(errorLabel);
        }
    }

    private boolean hasAccountChanges() {
        if (!nameField.getText().trim().equals(initialState.getName()))
            return true;

        if (!surnameField.getText().trim().equals(initialState.getSurname()))
            return true;

        if (!dateOfBirthField.getValue().equals(initialState.getDateOfBirth()))
            return true;

        if (!positionField.getText().trim().equals(initialState.getPosition().toString()))
            return true;

        return false;
    }

    @FXML
    private void backToHome() {
        if (hasAccountChanges()) {
            boolean confirm = showConfirmation(
                    "Sei sicuro di voler tornare indietro?",
                    "Le modifiche andranno perse.");

            if (!confirm) return;
        }

        setNodeVisibility(accountPanel, false);
        organizeMatch.setVisible(true);
        buttonBox.setMouseTransparent(false);
    }

    @FXML
    private void backToAccountPanel() {
        boolean confirm = showConfirmation(
                "Sei sicuro di voler tornare indietro?",
                "La password rimarrà invariata.");

        if (!confirm) return;

        showAccount();
    }

    @Override
    public void reset() {
        passwordField.clear();
        confirmPasswordField.clear();

        setNodeVisibility(errorLabel, false);
        setNodeVisibility(errorLabel1, false);
        setNodeVisibility(accountPanel, false);
        setNodeVisibility(changePasswordPanel, false);

        isVisible = false;
        setNodeVisibility(passwordTextField, false);
        setNodeVisibility(passwordField, true);

        isConfirmPasswordVisible = false;
        setNodeVisibility(confirmPasswordTextField, false);
        setNodeVisibility(confirmPasswordField, true);

        setEyeIcon();
        populateFields();
    }

    @FXML
    private void showDatePicker() {
        dateOfBirthField.show();
    }

    @FXML
    private void logout() {
        boolean confirm = showConfirmation(
                "Vuoi effettuare il logut?",
                "Verrai reindirizzato alla pagina di login");

        if (!confirm) return;

        switchTo("Login", "Altro");
    }
}