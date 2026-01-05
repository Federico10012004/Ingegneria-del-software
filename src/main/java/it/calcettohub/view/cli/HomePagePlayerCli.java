package it.calcettohub.view.cli;

import it.calcettohub.utils.PageManager;

public class HomePagePlayerCli extends AbstractHomePageCli {

    @Override
    protected String getHomeTitle() {
        return "Home Player";
    }

    @Override
    protected String[] getSpecificOption() {
        return new String[] {
                "Prenota campo",
                "Organizza nuova partita"
        };
    }

    @Override
    protected void onSpecificOption(int choice) {
        switch (choice) {
            case 1 -> PageManager.push(() -> new SearchFieldCli().search());
            case 2 -> {
                print("Funzionalità non ancora implementata.");
                requestString("Premi INVIO per tornare alla Home");
            }
            default -> throw new IllegalStateException("Scelta non valida: " + choice);
        }
    }
}