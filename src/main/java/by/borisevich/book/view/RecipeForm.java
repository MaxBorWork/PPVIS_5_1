package by.borisevich.book.view;

import by.borisevich.book.recipe.Recipe;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.util.Map;

class RecipeForm {

    private Text recipeTitleText;
    private Text recipeCategoryText;
    private Text recipePersonText;
    private Text recipeTimeText;
    private Text recipeTextText;
    private Text recipeIngredientsText;

    RecipeForm(MainWindow window, Shell shell) {

        Composite recipeTitle = new Composite(shell, SWT.NONE);
        recipeTitle.setLayout(new GridLayout(2, false));
        Label recipeTitleLabel = new Label(recipeTitle, SWT.NONE);
        recipeTitleText = window.createTextField(recipeTitle, recipeTitleLabel,
                "название");

        Composite recipeCategory = new Composite(shell, SWT.NONE);
        recipeCategory.setLayout(new GridLayout(2, false));
        Label recipeCategoryLabel = new Label(recipeCategory, SWT.NONE);
        recipeCategoryText = window.createTextField(recipeCategory, recipeCategoryLabel,
                "категорию");

        Composite recipePerson = new Composite(shell, SWT.NONE);
        recipePerson.setLayout(new GridLayout(2, false));
        Label recipePersonLabel = new Label(recipePerson, SWT.NONE);
        recipePersonText = window.createTextField(recipePerson, recipePersonLabel,
                "количество персон");

        Composite recipeTime = new Composite(shell, SWT.NONE);
        recipeTime.setLayout(new GridLayout(2, false));
        Label recipeTimeLabel = new Label(recipeTime, SWT.NONE);
        recipeTimeText = window.createTextField(recipeTime, recipeTimeLabel,
                "время приготовления");

        Composite recipeText = new Composite(shell, SWT.NONE);
        recipeText.setLayout(new GridLayout(2, false));
        Label recipeTextLabel = new Label(recipeText, SWT.NONE);
        recipeTextLabel.setText("Введите текстовое описание: ");
        recipeTextText = new Text(recipeText, SWT.NONE);
        recipeTextText.setLayoutData(new GridData(300, 100));

        Composite recipeIngredients = new Composite(shell, SWT.NONE);
        recipeIngredients.setLayout(new GridLayout(2, false));
        Label recipeIngredientsLabel = new Label(recipeIngredients, SWT.NONE);
        recipeIngredientsLabel.setText("Введите текстовое описание: ");
        recipeIngredientsText = new Text(recipeIngredients, SWT.NONE);
        recipeIngredientsText.setLayoutData(new GridData(300, 100));
        
    }

    RecipeForm(MainWindow window, Shell shell, Recipe foundRecipe) {

        Composite recipeTitle = new Composite(shell, SWT.NONE);
        recipeTitle.setLayout(new GridLayout(2, false));
        Label recipeTitleLabel = new Label(recipeTitle, SWT.NONE);
        recipeTitleText = window.createTextField(recipeTitle, recipeTitleLabel,
                "название");

        Composite recipeCategory = new Composite(shell, SWT.NONE);
        recipeCategory.setLayout(new GridLayout(2, false));
        Label recipeCategoryLabel = new Label(recipeCategory, SWT.NONE);
        recipeCategoryText = window.createTextField(recipeCategory, recipeCategoryLabel,
                "категорию");

        Composite recipePerson = new Composite(shell, SWT.NONE);
        recipePerson.setLayout(new GridLayout(2, false));
        Label recipePersonLabel = new Label(recipePerson, SWT.NONE);
        recipePersonText = window.createTextField(recipePerson, recipePersonLabel,
                "количество персон");

        Composite recipeTime = new Composite(shell, SWT.NONE);
        recipeTime.setLayout(new GridLayout(2, false));
        Label recipeTimeLabel = new Label(recipeTime, SWT.NONE);
        recipeTimeText = window.createTextField(recipeTime, recipeTimeLabel,
                "время приготовления");

        Composite recipeText = new Composite(shell, SWT.NONE);
        recipeText.setLayout(new GridLayout(2, false));
        Label recipeTextLabel = new Label(recipeText, SWT.NONE);
        recipeTextLabel.setText("Введите текстовое описание: ");
        recipeTextText = new Text(recipeText, SWT.NONE);
        recipeTextText.setLayoutData(new GridData(300, 100));

        Composite recipeIngredients = new Composite(shell, SWT.NONE);
        recipeIngredients.setLayout(new GridLayout(2, false));
        Label recipeIngredientsLabel = new Label(recipeIngredients, SWT.NONE);
        recipeIngredientsLabel.setText("Введите ингредиенты: ");
        recipeIngredientsText = new Text(recipeIngredients, SWT.NONE);
        recipeIngredientsText.setLayoutData(new GridData(300, 100));

        if (foundRecipe.getTitle() != null) {
            StringBuilder ingredients = new StringBuilder();
            recipeTitleText.setText(foundRecipe.getTitle());
            recipeCategoryText.setText(foundRecipe.getCategory());
            recipePersonText.setText(String.valueOf(foundRecipe.getColOfPerson()));
            recipeTimeText.setText(foundRecipe.getCookingTime());
            recipeTextText.setText(foundRecipe.getTextInstruction());

            for (Map.Entry<String, String> entry : foundRecipe.getIngredientsMap().entrySet()) {
                ingredients.append("\n").append(entry.getKey()).append("  -   ").append(entry.getValue());
            }
            recipeIngredientsText.setText(ingredients.toString());
        }
    }

    public Text getRecipeTitleText() {
        return recipeTitleText;
    }

    public Text getRecipeCategoryText() {
        return recipeCategoryText;
    }

    public Text getRecipePersonText() {
        return recipePersonText;
    }

    public Text getRecipeTimeText() {
        return recipeTimeText;
    }

    public Text getRecipeTextText() {
        return recipeTextText;
    }

    public Text getRecipeIngredientsText() {
        return recipeIngredientsText;
    }
}
