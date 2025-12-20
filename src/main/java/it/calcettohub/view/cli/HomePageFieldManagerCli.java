package it.calcettohub.view.cli;

import it.calcettohub.util.PageManager;

public class HomePageFieldManagerCli extends AbstractHomePageCli {

    protected String getHomeTitle() {
        return "Home Field Manager";
    }

    protected String[] getSpecificOption() {
        return new String[] {
                "Gestisci campi",
                "Gestisci prenotazioni"
        };
    }

    protected void onFirstOption() {
        PageManager.push(() -> new FieldManagementCli().start());
    }

    protected void onSecondOption() {
        System.out.println("Gestisci prenotazioni");
    }
}