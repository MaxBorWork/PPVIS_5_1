package by.borisevich.book.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeController {

    private RecipeDao recipeDao = new RecipeDao();

    public void addRecipe(Recipe recipe) {
        recipeDao.addRecipe(recipe);
    }

    public List<Recipe> showRecipeList() {
        List<Recipe> recipes = recipeDao.listRecipe();
        for (Recipe recipe : recipes) {
            Map<String, String> ingredientsMap = new HashMap<String, String>();
            for (Map.Entry<String, String> entry: recipe.getIngredientsMap().entrySet()) {
                ingredientsMap.put(entry.getKey(), entry.getValue());
            }
            recipe.setIngredientsMap(ingredientsMap);
        }
        return recipes;
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

    public Map<String, String> calculateIngredients(String title, int colOfPerson) {
        Recipe recipe = recipeDao.getRecipeByTitle(title);
        Map<String, String> recipeIngredients = recipe.getIngredientsMap();
        double personRatio = (double)colOfPerson/(double)recipe.getColOfPerson();

        for (Map.Entry<String, String> entry : recipeIngredients.entrySet()) {
            String key = entry.getKey(), value = entry.getValue();
            if (key.length() > 6 && key.charAt(key.length() - 6) == ' ') {
                key = key.substring(0, (key.length() - 6));
            }
            if (value.length() > 8 && value.charAt(8) == ' ') {
                value = value.substring(9);
            }
            String[] ingredientSlice = value.split(" ");
            if (ingredientSlice.length > 0 && !ingredientSlice[0].equals("")) {
                if (ingredientSlice[0].contains(",")) {
                    ingredientSlice[0] = ingredientSlice[0].replace(',', '.');
                }
                try {
                    double newIngredientAmount = (Double.parseDouble(ingredientSlice[0]) * personRatio);
                    entry.setValue(Double.toString(newIngredientAmount) + " " + ingredientSlice[1]);
                } catch (Exception e) {
                    System.out.println("Can't convert to double");
                }
            }
        }

        return recipeIngredients;
    }

}
