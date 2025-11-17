package it.calcettohub;

import it.calcettohub.util.PageManager;
import it.calcettohub.view.cli.RoleSelectionCli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainProva {
    public static void main(String[] args) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Benvenuto su CalcettoHub!");
        while (true) {
            System.out.println("Seleziona interfaccia: ");
            System.out.println("1) CLI");
            System.out.println("2) GUI (JavaFX)");
            try {
                int select = Integer.parseInt(reader.readLine());

                if (select == 1) {
                    PageManager.push(()->new RoleSelectionCli().start());
                    break;
                } else if (select == 2) {
                    System.out.println("Gui da implmentare");
                    break;
                } else {
                    System.err.println("Inserire 1 o 2.");
                }
            } catch (NumberFormatException | IOException e) {
                System.err.println("Input non valido, inserire un numero.");
            }
        }
    }
}
