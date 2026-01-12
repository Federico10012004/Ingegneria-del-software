package it.calcettohub.view.gui;

import it.calcettohub.bean.AccountBean;
import it.calcettohub.bean.PlayerAccountBean;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.utils.SessionManager;
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
    @FXML private VBox fieldBooking;

    private Player currentPlayer;
    private PlayerAccountBean initialState;

    @FXML
    private void initialize() {
        initializeCommon();

        setupResponsiveLabel(matchLabel, root, 45.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(fieldLabel, root, 45.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(reservationLabel, root, 45.0, FONT_STYLE_TAHOMA);

        setUpResponsiveIcon(matchIcon, root, 0.065);
        setUpResponsiveIcon(fieldIcon, root, 0.065);
        setUpResponsiveIcon(reservationIcon, root, 0.065);

        populateFields();
        setupAccountChangeListeners();
    }

    @Override
    protected void populateFields() {
        currentPlayer = (Player) SessionManager.getInstance().getLoggedUser();
        helloLabel.setText("Ciao " + currentPlayer.getEmail());

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
        reservation.setVisible(false);
        buttonBox.setMouseTransparent(true);
    }

    @Override
    protected void showNotifications() {
        setNodeVisibility(notificationPanel, true);
        fieldBooking.setVisible(false);
        buttonBox.setMouseTransparent(true);

        int count = loadNotifications();
        if (count > 0) {
            notificationController.markAsAlreadyRead();
        }
    }

    @Override
    protected void closeNotificationBox() {
        setNodeVisibility(notificationPanel, false);
        fieldBooking.setVisible(true);
        buttonBox.setMouseTransparent(false);
    }

    @Override
    protected AccountBean createAccountBean() {
        return new PlayerAccountBean();
    }

    @Override
    protected void fillAccountBean(AccountBean bean) {
        PlayerAccountBean playerBean = (PlayerAccountBean) bean;
        validateField(()-> playerBean.setName(nameField.getText().trim()));
        validateField(()-> playerBean.setSurname(surnameField.getText().trim()));
        validateField(()-> playerBean.setPosition(PlayerPosition.fromString(positionField.getText().trim())));
    }

    @Override
    protected void applyChanges(AccountBean bean) {
        PlayerAccountBean playerBean = (PlayerAccountBean) bean;
        currentPlayer.setName(playerBean.getName());
        currentPlayer.setSurname(playerBean.getSurname());
        currentPlayer.setPreferredPosition(playerBean.getPosition());

        initialState.setName(playerBean.getName());
        initialState.setSurname(playerBean.getSurname());
        initialState.setPosition(playerBean.getPosition());
    }

    @Override
    protected void resetHomeContent() {
        setNodeVisibility(reservation, true);
        setNodeVisibility(fieldBooking, true);
        buttonBox.setMouseTransparent(false);
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
        reservation.setVisible(true);
        buttonBox.setMouseTransparent(false);
        reset();
    }

    @Override
    protected void logout() {
        boolean confirm = showConfirmation(
                "Vuoi effettuare il logut?",
                "Verrai reindirizzato alla pagina di login");

        if (!confirm) return;

        SessionManager.getInstance().closeSession();

        organizeMatch.setVisible(true);
        buttonBox.setMouseTransparent(false);

        switchTo("Login");
    }

    @FXML
    private void goToOrganizeMatch() {
        showInfo("Funzionalità non implementata");
    }

    @FXML
    private void goToSearchField() {
        switchTo("Search Field");
    }
}