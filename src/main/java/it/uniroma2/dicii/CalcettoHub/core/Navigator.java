package it.uniroma2.dicii.CalcettoHub.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.uniroma2.dicii.CalcettoHub.controller.Resettable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Navigator centralizzato per la gestione delle schermate dell'app CalcettoHub.
 */
public class Navigator {

    private static Stage mainStage;
    private static final Map<String, Scene> scenes = new HashMap<>();

    // Imposta lo stage principale dell'app
    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    // Carica una scena FXML e memorizza sia la scena che il controller
    public static void loadScene(String name, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // 🔹 Salva anche il controller, così possiamo richiamarlo dopo
        scene.setUserData(loader.getController());
        scenes.put(name, scene);
    }

    // Mostra una scena già caricata
    public static void show(String name) {
        Scene scene = scenes.get(name);
        if (scene == null) {
            System.err.println("⚠️ Scena non trovata: " + name);
            return;
        }

        Object controller = scene.getUserData();
        if (controller instanceof Resettable resettable) {
            resettable.reset();
        }

        boolean wasFullScreen = mainStage.isFullScreen();
        double width = mainStage.getWidth();
        double height = mainStage.getHeight();

        mainStage.setScene(scene);
        mainStage.setWidth(width);
        mainStage.setHeight(height);
        mainStage.setFullScreen(wasFullScreen);
        mainStage.show();
    }


    // Ottieni lo Stage principale (es. per binding o modali)
    public static Stage getMainStage() {
        return mainStage;
    }
}
