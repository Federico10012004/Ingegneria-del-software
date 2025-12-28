package it.calcettohub.view.cli;

import it.calcettohub.utils.PageManager;

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
        PageManager.push(() -> new SearchFieldCli().search());
    }

    protected void onSecondOption() {
        System.out.println("Organizza nuova partita");
    }
}