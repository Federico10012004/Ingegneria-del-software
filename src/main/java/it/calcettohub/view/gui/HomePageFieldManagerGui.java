package it.calcettohub.view.gui;

import it.calcettohub.bean.AccountBean;
import it.calcettohub.bean.FieldManagerAccountBean;
import it.calcettohub.model.FieldManager;
import it.calcettohub.util.SessionManager;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class HomePageFieldManagerGui extends AbstractHomePageGui {
    @FXML private ImageView reservationIcon;
    @FXML private Label reservationLabel;
    @FXML private TextField phoneField;
    @FXML private VBox reservation;

    private FieldManager currentManager;
    private FieldManagerAccountBean initialState;

    @FXML
    private void initialize() {
        initializeCommon();

        setupResponsiveLabel(reservationLabel, root, 30.0, FONT_STYLE_TAHOMA);
        setUpResponsiveIcon(reservationIcon, root, 0.075);

        currentManager = (FieldManager) SessionManager.getInstance().getLoggedUser();
        helloLabel.setText("Ciao " + currentManager.getEmail());
        populateFields();
        setupAccountChangeListeners();
    }

    @Override
    protected void populateFields() {
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
    protected boolean hasAccountChanges() {
        if (!nameField.getText().trim().equals(initialState.getName()))
            return true;

        if (!surnameField.getText().trim().equals(initialState.getSurname()))
            return true;

        return !phoneField.getText().trim().equals(initialState.getPhoneNumber());
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

        reservation.setVisible(true);
        buttonBox.setMouseTransparent(false);

        switchTo("Login");
    }
}
