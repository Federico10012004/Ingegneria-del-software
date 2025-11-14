package it.calcettohub;

import it.calcettohub.view.cli.RoleSelectionCli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainProva {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Benvenuto su CalcettoHub!");
            System.out.println("Seleziona interfaccia: ");
            System.out.println("1) CLI");
            System.out.println("2) GUI (JavaFX)");

            int scelta = Integer.parseInt(reader.readLine());

            if (scelta == 1) {
                new RoleSelectionCli().start();
            } else {
                System.out.println("Gui da implmentare");
            }
        } catch (IOException _) {

        }
    }
}
