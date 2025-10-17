package it.uniroma2.dicii.CalcettoHub;

import javafx.application.Application;
import javafx.stage.Stage;
import it.uniroma2.dicii.CalcettoHub.core.Navigator;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Navigator.setStage(stage);

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
