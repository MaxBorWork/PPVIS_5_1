package by.borisevich.recipeBook.service.impl;

import by.borisevich.recipeBook.dao.impl.RecipeDaoImpl;
import by.borisevich.recipeBook.model.Recipe;
import by.borisevich.recipeBook.service.RecipeService;

import java.util.List;

public class RecipeServiceImpl implements RecipeService {

    private RecipeDaoImpl recipeDao = new RecipeDaoImpl();

    public void addRecipe(Recipe recipe) {
        recipeDao.addRecipe(recipe);
    }

    public List<Recipe> listRecipe() {
        return recipeDao.listRecipe();
    }

    public Recipe getRecipeByTitle(String title) {
        return recipeDao.getRecipeByTitle(title);
    }

    public void updateRecipe(Recipe recipe) {
        recipeDao.updateRecipe(recipe);
    }

    public void deleteRecipe(String title) {
        recipeDao.deleteRecipe(title);
    }
}
