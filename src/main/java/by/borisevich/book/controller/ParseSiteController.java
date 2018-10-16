package by.borisevich.book.controller;

import by.borisevich.book.recipe.Recipe;
import by.borisevich.book.recipe.RecipeController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseSiteController {

    private final String SITE_URL = "http://sovkusom.ru/recepty-naznachenie/zakuski/";
    private List<String> recipeUrlList = new ArrayList<String>();
    private RecipeController recipeController = new RecipeController();

    public void parseCatalog() {
        int numOfPages = getColOfPages();
        for (int pageIndex = 1; pageIndex <= numOfPages; pageIndex++) {
            String url = SITE_URL + "page/" + pageIndex;
            Document document = null;
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements element = null;
            if (document != null) {
                element = document.select(".recipe-item h2 a");
            }
            if (element != null) {
                for (Element elem : element) {
                    String recipeUrl = elem.attr("href");
                    if (recipeController.getRecipeByTitle(recipeUrl) == null)
                    recipeUrlList.add(recipeUrl);
                }
            }
        }
        parseRecipe();
    }

    private int getColOfPages() {
        Document document = null;
        try {
            document = Jsoup.connect(SITE_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = null;
        if (document != null) {
            element = document.select(".pager span a");
        }
        Element lastPage = null;
        if (element != null) {
            lastPage = element.get(2);
        }
        String numOfPages = lastPage != null ? lastPage.text() : null;
        if (numOfPages == null) {
            return 0;
        }
        return  Integer.parseInt(numOfPages);
    }

    private void parseRecipe() {
        for (String recipeUrl : recipeUrlList) {
            Document document = null;
            try {
                document = Jsoup.connect(recipeUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String recipeTitle = getStringFromElementBySelector(document, "h1", 0);

            Map<String, String> recipeIngredients = getIngredientsMap(document);

            String recipeCategory = getStringFromElementBySelector(document, ".basic .item", 0);
            recipeCategory = recipeCategory.replace("Тип:", "");

            String recipeCookingTime = getStringFromElementBySelector(document, ".basic .item", 1);
            recipeCookingTime = recipeCookingTime.replace("Время:", "");

            String recipeColOfPerson = getStringFromElementBySelector(document, ".basic .item", 3);
            recipeColOfPerson = recipeColOfPerson.replace("Порций: ", "");

            String recipeTextInstruction = getTextInstruction(document);

            Recipe recipe = new Recipe(recipeTitle, recipeCategory, recipeUrl, recipeIngredients,
                                        recipeTextInstruction, Integer.parseInt(recipeColOfPerson),
                                        recipeCookingTime);

            recipeController.addRecipe(recipe);
        }
    }

    private String getStringFromElementBySelector(Document document, String selector, int listIndex) {
        Elements elements = null;
        if (document != null) {
            elements = document.select(selector);
        }
        Element recipeInfoElem = null;
        if (elements != null) {
            recipeInfoElem = elements.get(listIndex);
        }
        String recipeInfo = null;
        if (recipeInfoElem != null) {
            recipeInfo = recipeInfoElem.text();
        }
        return recipeInfo;
    }

    private Map<String, String> getIngredientsMap(Document document) {
        Map<String, String> recipeIngredients = new HashMap<String, String>();
        Elements elements = null;
        if (document != null) {
            elements = document.select(".ingredients-container .ingredients");
        }
        if (elements != null) {
            for (Element elem : elements) {
                String ingredientAmount = elem.select("dd").text();
                String ingredientTitle = elem.select("dt a").text();

                recipeIngredients.put(ingredientTitle, ingredientAmount);
            }
        }
        return recipeIngredients;
    }

    private String getTextInstruction(Document document) {
        StringBuilder textInstruction = new StringBuilder();
        Elements elements = null;
        if (document != null) {
            elements = document.select(".instructions.recipe-instructions ol li span");
        }
        if (elements != null) {
            for (Element elem : elements) {
                if (!elem.hasClass("number")) {
                    String textInstructionParagraph = elem.text();
                    textInstruction.append(" \n ").append(textInstructionParagraph);
                }
            }
        }
        return textInstruction.toString();
    }
}
