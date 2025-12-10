package it.calcettohub.view.gui;

import it.calcettohub.bean.PlayerAccountBean;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.SessionManager;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class HomePagePlayerGui extends AbstractHomePageGui {
    @FXML private ImageView matchIcon;
    @FXML private Label matchLabel;
    @FXML private TextField positionField;
    @FXML private VBox organizeMatch;

    private Player currentPlayer;
    private PlayerAccountBean initialState;

    @FXML
    private void initialize() {
        initializeCommon();

        setupResponsiveLabel(matchLabel, root, 30.0, FONT_STYLE_TAHOMA);
        setUpResponsiveIcon(matchIcon, root, 0.075);

        currentPlayer = (Player) SessionManager.getInstance().getLoggedUser();
        helloLabel.setText("Ciao " + currentPlayer.getEmail());
        populateFields();
        setupAccountChangeListeners();
    }

    @Override
    protected void populateFields() {
        nameField.setText(currentPlayer.getName());
        surnameField.setText(currentPlayer.getSurname());
        positionField.setText(currentPlayer.getPreferredPosition().toString());

        initialState = new PlayerAccountBean();
        initialState.setName(currentPlayer.getName());
        initialState.setSurname(currentPlayer.getSurname());
        initialState.setPosition(currentPlayer.getPreferredPosition());
        initialState.setPassword(currentPlayer.getPassword());
    }

    @Override
    protected void setupAccountChangeListeners() {
        ChangeListener<Object> listener = (_, _, _) -> updateConfirmButtonVisibility();

        nameField.textProperty().addListener(listener);
        surnameField.textProperty().addListener(listener);
        positionField.textProperty().addListener(listener);
    }

    @Override
    protected void showAccount() {
        setNodeVisibility(accountPanel, true);
        setNodeVisibility(changePasswordPanel, false);
        organizeMatch.setVisible(false);
        buttonBox.setMouseTransparent(true);
    }

    /*@Override
    protected void applyChanges(AccountBean bean) {
        currentPlayer.setName(bean.getName());
        currentPlayer.setSurname(bean.getSurname());
        currentPlayer.setPreferredPosition(bean.getPosition());

        initialState.setName(bean.getName());
        initialState.setSurname(bean.getSurname());
        initialState.setPosition(bean.getPosition());
    }*/

    @FXML
    private void modifyAccount() {
        PlayerAccountBean bean = new PlayerAccountBean();

        if (!hasAccountChanges()) {
            return;
        }

        try {
            validateField(()-> bean.setName(nameField.getText().trim()));
            validateField(()-> bean.setSurname(surnameField.getText().trim()));
            validateField(()-> bean.setPosition(PlayerPosition.fromString(positionField.getText().trim())));

            controller.updateUserData(bean);

            currentPlayer.setName(bean.getName());
            currentPlayer.setSurname(bean.getSurname());
            currentPlayer.setPreferredPosition(bean.getPosition());

            initialState.setName(bean.getName());
            initialState.setSurname(bean.getSurname());
            initialState.setPosition(bean.getPosition());

            showInfo("Modifiche applicate con successo.");
            setNodeVisibility(confirmModifyButton, false);
            populateFields();
        } catch (IllegalArgumentException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showError(errorLabel);
        }
    }

    @Override
    protected boolean hasAccountChanges() {
        if (!nameField.getText().trim().equals(initialState.getName()))
            return true;

        if (!surnameField.getText().trim().equals(initialState.getSurname()))
            return true;

        return !positionField.getText().trim().equals(initialState.getPosition().toString());
    }

    @Override
    protected void backToHome() {
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

    @Override
    protected void logout() {
        boolean confirm = showConfirmation(
                "Vuoi effettuare il logut?",
                "Verrai reindirizzato alla pagina di login");

        if (!confirm) return;

        organizeMatch.setVisible(true);
        buttonBox.setMouseTransparent(false);

        switchTo("Login", "Altro");
    }
}