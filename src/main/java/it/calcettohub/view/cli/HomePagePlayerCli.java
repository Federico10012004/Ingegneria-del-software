package it.calcettohub.view.cli;

public class HomePagePlayerCli extends AbstractHomePageCli {

    protected String getHomeTitle() {
        return "Home Player";
    }

    protected String[] getSpecificOption() {
        return new String[] {
                "Prenota campo",
                "Organizza nuova partita"
        };
    }

    protected void onFirstOption() {
        System.out.println("Prenota campo");
    }

    protected void onSecondOption() {
        System.out.println("Organizza nuova partita");
    }
}