package it.calcettohub.view.cli;

import it.calcettohub.utils.PageManager;

public class HomePageFieldManagerCli extends AbstractHomePageCli {

    @Override
    protected String getHomeTitle() {
        return "Home Field Manager";
    }

    @Override
    protected String[] getSpecificOption() {
        return new String[] {
                "Gestisci campi"
        };
    }

    @Override
    protected void onSpecificOption(int choice) {
        PageManager.push(() -> new FieldManagementCli().start());
    }
}