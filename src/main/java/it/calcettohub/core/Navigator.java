package it.calcettohub.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.calcettohub.controller.Resettable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Navigator centralizzato per la gestione delle schermate dell'app CalcettoHub.
 * Versione con caricamento lazy (on-demand).
 */
public class Navigator {

    private static Stage mainStage;
    private static final Map<String, Scene> scenes = new HashMap<>();

    private static String userType;
    private static String previousPage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    // Tipo utente (gestore, giocatore, arbitro)
    public static void setUserType(String type) {
        userType = type;
    }

    public static String getUserType() {
        return userType;
    }

    public static void setPreviousPage(String page) {
        previousPage = page;
    }

    public static String getPreviousPage() {
        return previousPage;
    }

    /**
     * Mostra una scena. Se non è stata ancora caricata, la carica automaticamente (lazy loading).
     */
    public static void show(String name) {
        try {
            Scene scene = scenes.get(name);

            // Lazy loading: se la scena non è ancora caricata, caricala ora
            if (scene == null) {
                FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(getFXMLPath(name)));
                Parent root = loader.load();
                scene = new Scene(root);
                scene.setUserData(loader.getController());
                scenes.put(name, scene);
            }

            Object controller = scene.getUserData();
            if (controller instanceof Resettable resettable) {
                resettable.reset(); // pulisci o reimposta la UI
            }

            mainStage.setScene(scene);
            mainStage.show();

        } catch (IOException _) {
            System.err.println("Errore nel caricamento della scena: " + name);
        }
    }

    /**
     * Restituisce il percorso FXML in base al nome logico della scena.
     * Questo metodo centralizza la gestione dei percorsi.
     */
    private static String getFXMLPath(String name) {
        return switch (name) {
            case "Login" -> "/fxml/login.fxml";
            case "Welcome" -> "/fxml/SchermataIniziale.fxml";
            case "Register" -> "/fxml/register.fxml";
            case "Dati Campo" -> "/fxml/datiCampo.fxml";
            default -> throw new IllegalArgumentException("Percorso FXML non definito per: " + name);
        };
    }

    private Navigator() {
        throw new UnsupportedOperationException("Classe di utilità, non deve essere istanziata");
    }
}
