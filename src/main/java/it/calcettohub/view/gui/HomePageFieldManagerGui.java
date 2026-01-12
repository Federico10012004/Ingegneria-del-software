package it.calcettohub.view.gui;

import it.calcettohub.bean.AccountBean;
import it.calcettohub.bean.FieldManagerAccountBean;
import it.calcettohub.model.FieldManager;
import it.calcettohub.utils.SessionManager;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class HomePageFieldManagerGui extends AbstractHomePageGui {
    @FXML private TextField phoneField;
    @FXML private VBox fieldManagement;

    private FieldManager currentManager;
    private FieldManagerAccountBean initialState;

    @FXML
    private void initialize() {
        initializeCommon();

        setupResponsiveLabel(fieldLabel, root, 40.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(reservationLabel, root, 40.0, FONT_STYLE_TAHOMA);

        setUpResponsiveIcon(fieldIcon, root, 0.070);
        setUpResponsiveIcon(reservationIcon, root, 0.070);

        populateFields();
        setupAccountChangeListeners();
    }

    @Override
    protected void populateFields() {
        currentManager = (FieldManager) SessionManager.getInstance().getLoggedUser();
        helloLabel.setText("Ciao " + currentManager.getEmail());

        nameField.setText(currentManager.getName());
        surnameField.setText(currentManager.getSurname());
        phoneField.setText(currentManager.getPhoneNumber());

        initialState = new FieldManagerAccountBean();
        initialState.setName(currentManager.getName());
        initialState.setSurname(currentManager.getSurname());
        initialState.setPhoneNumber(currentManager.getPhoneNumber());
        initialState.setPassword(currentManager.getPassword());
    }

    @Override
    protected void setupAccountChangeListeners() {
        ChangeListener<Object> listener = (_, _, _) -> updateConfirmButtonVisibility();

        nameField.textProperty().addListener(listener);
        surnameField.textProperty().addListener(listener);
        phoneField.textProperty().addListener(listener);
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
        fieldManagement.setVisible(false);
        buttonBox.setMouseTransparent(true);

        int count = loadNotifications();
        if (count > 0) {
            notificationController.markAsAlreadyRead();
        }
    }

    @Override
    protected void closeNotificationBox() {
        setNodeVisibility(notificationPanel, false);
        fieldManagement.setVisible(true);
        buttonBox.setMouseTransparent(false);
    }

    @Override
    protected AccountBean createAccountBean() {
        return new FieldManagerAccountBean();
    }

    @Override
    protected void fillAccountBean(AccountBean bean) {
        FieldManagerAccountBean managerBean = (FieldManagerAccountBean) bean;
        validateField(()-> managerBean.setName(nameField.getText().trim()));
        validateField(()-> managerBean.setSurname(surnameField.getText().trim()));
        validateField(()-> managerBean.setPhoneNumber(phoneField.getText().trim()));
    }

    @Override
    protected void applyChanges(AccountBean bean) {
        FieldManagerAccountBean managerBean = (FieldManagerAccountBean) bean;
        currentManager.setName(managerBean.getName());
        currentManager.setSurname(managerBean.getSurname());
        currentManager.setPhoneNumber(managerBean.getPhoneNumber());

        initialState.setName(managerBean.getName());
        initialState.setSurname(managerBean.getSurname());
        initialState.setPhoneNumber(managerBean.getPhoneNumber());
    }

    @Override
    protected void resetHomeContent() {
        setNodeVisibility(reservation, true);
        setNodeVisibility(fieldManagement, true);
        buttonBox.setMouseTransparent(false);
    }

    @Override
    protected boolean hasAccountChanges() {
        if (!nameField.getText().trim().equals(initialState.getName()))
            return true;

        if (!surnameField.getText().trim().equals(initialState.getSurname()))
            return true;

        return !phoneField.getText().trim().equals(initialState.getPhoneNumber());
    }

    @Override
    protected void logout() {
        boolean confirm = showConfirmation(
                "Vuoi effettuare il logut?",
                "Verrai reindirizzato alla pagina di login");

        if (!confirm) return;

        SessionManager.getInstance().closeSession();

        reservation.setVisible(true);
        buttonBox.setMouseTransparent(false);

        switchTo("Login");
    }

    @FXML
    private void goToFieldManagement() {
        switchTo("Field Management");
    }
}
