package it.calcettohub.utils;

import it.calcettohub.view.gui.BaseFormerGui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Navigator centralizzato per la gestione delle schermate dell'app CalcettoHub.
 * Versione con caricamento lazy (on-demand).
 */
public class Navigator {

    private Navigator() {}

    private static Stage mainStage;
    private static final Map<String, Scene> scenes = new HashMap<>();

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    /**
     * Mostra una scena. Se non è stata ancora caricata, la carica automaticamente (lazy loading).
     */
    public static void show(String name) {
        try {
            Scene scene = scenes.get(name);

            if (scene == null) {
                FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(getFXMLPath(name)));
                Parent root = loader.load();
                scene = new Scene(root);
                scene.setUserData(loader.getController());
                scenes.put(name, scene);
            }

            BaseFormerGui controller = (BaseFormerGui) scene.getUserData();

            controller.installSessionFilters(scene);

            controller.reset();

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
            case "Home Field Manager" -> "/fxml/HomePageFieldManager.fxml";
            case "Field Management" -> "/fxml/FieldManagement.fxml";
            case "Manage Bookings" -> "/fxml/ManageBookings.fxml";
            case "Search Field" -> "/fxml/SearchField.fxml";
            case "Field Booking" -> "/fxml/FieldBooking.fxml";
            default -> throw new IllegalArgumentException("Percorso FXML non definito per: " + name);
        };
    }
}
