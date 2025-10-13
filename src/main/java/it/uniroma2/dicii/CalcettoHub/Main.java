package it.uniroma2.dicii.CalcettoHub;

import javafx.application.Application;
import javafx.stage.Stage;
import it.uniroma2.dicii.CalcettoHub.core.Navigator;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Navigator.setStage(stage);

        // Carica le schermate principali (una sola volta)
        Navigator.loadScene("Login", "/fxml/login.fxml");
        Navigator.loadScene("Welcome", "/fxml/SchermataIniziale.fxml");
        Navigator.loadScene("Register", "/fxml/register.fxml");

        // Mostra la schermata iniziale
        Navigator.show("Welcome");

        stage.setWidth(1000);
        stage.setHeight(700);
        stage.centerOnScreen();

        stage.setTitle("CalcettoHub");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
