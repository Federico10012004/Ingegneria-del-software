package it.calcettohub.view.cli;

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
        System.out.println("Gestisci campi");
    }

    protected void onSecondOption() {
        System.out.println("Gestisci prenotazioni");
    }
}