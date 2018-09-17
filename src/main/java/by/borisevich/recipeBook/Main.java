package by.borisevich.recipeBook;

import by.borisevich.recipeBook.controller.ParseSiteController;
import by.borisevich.recipeBook.view.StartPage;

public class Main {

    public static void main(String[] args) {
        ParseSiteController controller = new ParseSiteController();
        controller.parseCatalog();
        new StartPage();
    }
}
