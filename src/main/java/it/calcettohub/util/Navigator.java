package it.calcettohub.util;

import it.calcettohub.model.Role;
import it.calcettohub.view.gui.BaseFormerGui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.calcettohub.view.gui.Resettable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Navigator centralizzato per la gestione delle schermate dell'app CalcettoHub.
 * Versione con caricamento lazy (on-demand).
 */
public class Navigator {

    private Navigator() {}

    // Qui memorizzi lo stage passato dal Main, così ogni scena può essere mostrata da qui
    private static Stage mainStage;

    // Questa mappa conserva le scene già caricate
    private static final Map<String, Scene> scenes = new HashMap<>();

    // Mantiene il tipo di utentee la pagina precedente
    private static Role userType;
    private static String previousPage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    // Tipo utente (gestore, giocatore, arbitro)
    public static void setUserType(Role type) {
        userType = type;
    }

    public static Role getUserType() {
        return userType;
    }

    public static void setPreviousPage(String page) {
        previousPage = page;
    }

    public static String getPreviousPage() {
        return previousPage;
    }

    public static void resetUserType() {
        userType = null;
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

            BaseFormerGui controller = (BaseFormerGui) scene.getUserData();

            // Installiamo i filtri di sessione
            controller.installSessionFilters(scene);

            controller.reset(); // pulisci o reimposta la UI

            mainStage.setScene(scene);
            mainStage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento della scena: " + name + e);
        }
    }

    /**
     * Restituisce il percorso FXML in base al nome logico della scena.
     * Questo metodo centralizza la gestione dei percorsi.
     */
    private static String getFXMLPath(String name) {
        return switch (name) {
            case "Login" -> "/fxml/Login.fxml";
            case "Role Selection" -> "/fxml/RoleSelection.fxml";
            case "Registration Gui" -> "/fxml/Registration.fxml";
            case "Home Player" -> "/fxml/HomePagePlayer.fxml";
            default -> throw new IllegalArgumentException("Percorso FXML non definito per: " + name);
        };
    }
}
