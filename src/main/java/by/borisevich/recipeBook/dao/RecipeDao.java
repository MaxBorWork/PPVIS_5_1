package by.borisevich.recipeBook.dao;

import by.borisevich.recipeBook.model.Recipe;

import java.util.List;

public interface RecipeDao {

    public void addRecipe(Recipe recipe);

    public List<Recipe> listRecipe();

    public Recipe getRecipeByTitle(String title);

    public void updateRecipe(Recipe recipe);

    public void deleteRecipe(String title);
}
