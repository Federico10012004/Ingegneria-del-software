package it.calcettohub;

import it.calcettohub.util.PageManager;
import it.calcettohub.view.cli.RoleSelectionCli;
import javafx.application.Application;
import javafx.stage.Stage;
import it.calcettohub.util.Navigator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main extends Application {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Benvenuto su CalcettoHub!");
        while (true) {
            System.out.println("Seleziona interfaccia: ");
            System.out.println("1) CLI");
            System.out.println("2) GUI (JavaFX)");

            try {
                System.out.print("Selezione: ");
                int select = Integer.parseInt(reader.readLine());

                if (select == 1) {
                    PageManager.push(()->new RoleSelectionCli().start());
                    break;
                } else if (select == 2) {
                    launch();
                    break;
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                System.err.println("Input non valido, inserire un numero.");
            } catch (IOException e) {
                System.err.println("Errore nella lettura dell'input.");
            } catch (IllegalArgumentException e) {
                System.err.println("Inserire 1 o 2.");
            }
        }
    }

    @Override
    public void start(Stage stage) {
        Navigator.setStage(stage);
        Navigator.show("Role Selection");

        stage.setWidth(1000);
        stage.setHeight(700);
        stage.centerOnScreen();

        stage.setTitle("Calcetto Hub");
        stage.show();
    }
}