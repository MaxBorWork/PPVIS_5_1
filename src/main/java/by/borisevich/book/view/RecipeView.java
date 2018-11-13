package by.borisevich.book.view;

import by.borisevich.book.recipe.RecipeController;
import by.borisevich.book.recipe.Recipe;
import by.borisevich.book.user.User;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.text.Collator;
import java.util.*;
import java.util.List;

class RecipeView {

    private RecipeController recipeController = new RecipeController();
    private Table mainTable;
    private MainWindow window;

    RecipeView(MainWindow window) {
        this.window = window;

    }

    void initTable() {
        mainTable = new Table(window.getShell(), SWT.MULTI | SWT.BORDER |
                SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL | SWT.CENTER);
        mainTable.setHeaderVisible(true);
        mainTable.setLinesVisible(true);

        TableColumn titleColumn = new TableColumn(mainTable, SWT.CENTER);
        titleColumn.setText("Название");
        titleColumn.setWidth(200);

        TableColumn categoryColumn = new TableColumn(mainTable, SWT.CENTER);
        categoryColumn.setText("Категория");
        categoryColumn.setWidth(150);

        TableColumn ingredientsColumn = new TableColumn(mainTable, SWT.CENTER);
        ingredientsColumn.setText("Ингредиенты");
        ingredientsColumn.setWidth(250);

        TableColumn colOfPersonColumn = new TableColumn(mainTable, SWT.CENTER);
        colOfPersonColumn.setText("Персон");
        colOfPersonColumn.setWidth(100);

        TableColumn cookingTimeColumn = new TableColumn(mainTable, SWT.CENTER);
        cookingTimeColumn.setText("Время");
        cookingTimeColumn.setWidth(100);

        mainTable.setLayoutData(new GridData(800, 400));

    }

    private Table initIngredientsTable(Shell shell, Map<String, String> ingredientsMap) {
        Table recipeIngredients = new Table(shell, SWT.MULTI | SWT.BORDER |
                SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL | SWT.CENTER);
        recipeIngredients.setHeaderVisible(true);
        recipeIngredients.setLinesVisible(true);
        recipeIngredients.setSize(300, 400);

        TableColumn keyColumn = new TableColumn(recipeIngredients, SWT.CENTER);
        keyColumn.setText("Название");
        keyColumn.setWidth(200);
        TableColumn valueColumn = new TableColumn(recipeIngredients, SWT.CENTER);
        valueColumn.setWidth(100);
        valueColumn.setText("Значение");

        for (Map.Entry<String, String> entry : ingredientsMap.entrySet()) {
            TableItem ingredientItem = new TableItem(recipeIngredients, SWT.CENTER);
            ingredientItem.setText(0, entry.getKey());
            ingredientItem.setText(1, entry.getValue());
        }

        return recipeIngredients;
    }

    void addRecipe() {
        final Shell addRecipeShell = new Shell(window.getDisplay(), SWT.DIALOG_TRIM | SWT.V_SCROLL | SWT.H_SCROLL);
        addRecipeShell.setText("Добавить рецепт");
        window.initLayout(addRecipeShell);

        final RecipeForm form = new RecipeForm(window, addRecipeShell);

        Button okButton = new Button(addRecipeShell, SWT.PUSH);
        okButton.setText("Добавить");
        okButton.setSize(100, 150);

        okButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                Map<String, String> ingredientsMap = new HashMap<String, String>();
                String ingredientsText = form.getRecipeIngredientsText().getText();;
                String[] ingredientsSlice = ingredientsText.split("\n");
                for (String ingredient : ingredientsSlice) {
                    String[] ingredientSlice = ingredient.split("-");
                    if (ingredientSlice.length > 1) {
                        ingredientsMap.put(ingredientSlice[0], ingredientSlice[1]);
                    }
                }

                Recipe recipe = new Recipe(
                        form.getRecipeTitleText().getText(),
                        form.getRecipeCategoryText().getText(),
                        "",
                        ingredientsMap,
                        form.getRecipeTextText().getText(),
                        Integer.parseInt(form.getRecipePersonText().getText()),
                        form.getRecipeTimeText().getText()
                );
                recipeController.addRecipe(recipe);
                window.updateShell();
                addRecipeShell.close();
            }
        });

        addRecipeShell.setSize(800 , 1100);
        addRecipeShell.open();
    }

    void findRecipe(final User user) {
        final Shell findRecipeShell = new Shell(window.getDisplay(), SWT.DIALOG_TRIM);
        findRecipeShell.setText("Найти рецепт");
        window.initLayout(findRecipeShell);

        final Composite recipeTitle = new Composite(findRecipeShell, SWT.NONE);
        recipeTitle.setLayout(new GridLayout(2, false));
        Label recipeTitleLabel = new Label(recipeTitle, SWT.NONE);
        final Text recipeTitleText = window.createTextField(recipeTitle, recipeTitleLabel,
                "название");

        final Button okButton = new Button(findRecipeShell, SWT.PUSH);
        okButton.setText("Найти");
        okButton.setSize(100, 150);

        okButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                showRecipe(recipeTitleText.getText(), user);

                findRecipeShell.close();
            }
        });

        findRecipeShell.setSize(800 , 1100);
        findRecipeShell.open();
    }

    void updateRecipe(final String title) {
        final Shell updateRecipeShell = new Shell(window.getDisplay(), SWT.DIALOG_TRIM);
        updateRecipeShell.setText("Изменить рецепт");
        window.initLayout(updateRecipeShell);

        final Recipe foundRecipe = recipeController.getRecipeByTitle(title);

        final RecipeForm form = new RecipeForm(window, updateRecipeShell, foundRecipe);

        Button okButton = new Button(updateRecipeShell, SWT.PUSH);
        okButton.setText("Записать");
        okButton.setSize(100, 150);

        updateRecipeShell.layout();

        okButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Map<String, String> ingredientsMap = new HashMap<String, String>();
                String ingredientsText = form.getRecipeIngredientsText().getText();;
                String[] ingredientsSlice = ingredientsText.split("\n");
                for (String ingredient : ingredientsSlice) {
                    String[] ingredientSlice = ingredient.split("-");
                    if (ingredientSlice.length > 1) {
                        ingredientsMap.put(ingredientSlice[0], ingredientSlice[1]);
                    }
                }

                Recipe recipe = new Recipe(
                    form.getRecipeTitleText().getText(),
                        form.getRecipeCategoryText().getText(),
                        foundRecipe.getCiteUrl(),
                        ingredientsMap,
                        form.getRecipeTextText().getText(),
                        Integer.parseInt(form.getRecipePersonText().getText()),
                        form.getRecipeTimeText().getText()
                );
                recipe.setId(foundRecipe.getId());

                recipeController.updateRecipe(recipe);
                window.updateShell();
            }
        });

        updateRecipeShell.setSize(800 , 1100);
        updateRecipeShell.open();
    }

    private void showRecipe(String title, User user) {
        final Recipe foundRecipe = recipeController.getRecipeByTitle(title);
        final Table[] newIngredients = new Table[1];
        final Shell recipeShell = new Shell(window.getDisplay(), SWT.DIALOG_TRIM | SWT.CENTER);
        recipeShell.setText(title);
        window.initLayout(recipeShell);

        Label recipeTitle = new Label(recipeShell, SWT.CENTER);
        recipeTitle.setText(foundRecipe.getTitle());

        Table infoTable = new Table(recipeShell, SWT.MULTI | SWT.BORDER |
                SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL | SWT.CENTER);
        infoTable.setHeaderVisible(true);
        infoTable.setLinesVisible(true);

        TableColumn categoryColumn = new TableColumn(infoTable, SWT.CENTER);
        categoryColumn.setText("Категория");
        categoryColumn.setWidth(200);
        TableColumn personColumn = new TableColumn(infoTable, SWT.CENTER);
        personColumn.setWidth(100);
        personColumn.setText("Персон");
        final TableColumn timeColumn = new TableColumn(infoTable, SWT.CENTER);
        timeColumn.setText("Время");
        timeColumn.setWidth(100);

        TableItem item = new TableItem(infoTable, SWT.CENTER);
        item.setText(0, foundRecipe.getCategory());
        item.setText(1, String.valueOf(foundRecipe.getColOfPerson()));
        item.setText(2, foundRecipe.getCookingTime());

        final Label recipeIngredientsTitle = new Label(recipeShell, SWT.CENTER);
        recipeIngredientsTitle.setText("Ингредиенты: ");

        final Table recipeIngredients = initIngredientsTable(recipeShell, foundRecipe.getIngredientsMap());

        if (user.getUsername() != null) {
            Label recipeInstruction = new Label(recipeShell, SWT.CENTER);
            recipeInstruction.setText(foundRecipe.getTextInstruction());
            recipeInstruction.setBounds(0, 0, 400, 400);
        }

        final Composite calcIngredients = new Composite(recipeShell, SWT.NONE);
        calcIngredients.setLayout(new GridLayout(1, false));

        Label recipeCalcIngredientsTitle = new Label(calcIngredients, SWT.CENTER);
        recipeCalcIngredientsTitle.setText("Расчет ингредиентов по количеству персон: ");

        final Composite requestColOfPerson = new Composite(calcIngredients, SWT.NONE);
        requestColOfPerson.setLayout(new GridLayout(3, false));
        Label requestColOfPersonLabel = new Label(requestColOfPerson, SWT.NONE);
        final Text requestColOfPersonText = window.createTextField(requestColOfPerson, requestColOfPersonLabel,
                "персон");
        final Button calculateButton = new Button(requestColOfPerson, SWT.PUSH);
        calculateButton.setText("Рассчитать");
        calculateButton.setSize(100, 150);

        calculateButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                Map<String, String> newIngredientsMap = recipeController.calculateIngredients(foundRecipe.getTitle(),
                        Integer.parseInt(requestColOfPersonText.getText()));

                recipeIngredientsTitle.dispose();
                recipeIngredients.dispose();
                try {
                    if (newIngredients[0].getItemCount() > 0) {
                        newIngredients[0].dispose();
                    }
                } catch (NullPointerException ex) {
                    System.out.println("no tables are found");
                } finally {
                    newIngredients[0] = initIngredientsTable(recipeShell, newIngredientsMap);
                    recipeShell.layout();
                }
            }
        });

        recipeShell.setSize(600 , 800);
        recipeShell.open();
    }

    void showRecipes() {
        List<Recipe> recipeList = recipeController.showRecipeList();
        for (Recipe recipe : recipeList) {
            TableItem item = new TableItem(mainTable, SWT.CENTER);
            item.setText(0, recipe.getTitle());
            item.setText(1, recipe.getCategory());
            StringBuilder ingredients = new StringBuilder();
            for (Map.Entry<String, String> entry : recipe.getIngredientsMap().entrySet()) {
                ingredients.append("\n").append(entry.getKey()).append("  -   ").append(entry.getValue());
            }
            item.setText(2, ingredients.toString());
            item.setText(3, String.valueOf(recipe.getColOfPerson()));
            item.setText(4, recipe.getCookingTime());
        }
        window.getShell().layout();
    }

    void deleteRecipe() {
        final Shell deleteRecipeShell = new Shell(window.getDisplay(), SWT.DIALOG_TRIM);
        deleteRecipeShell.setText("Удалить рецепт");
        window.initLayout(deleteRecipeShell);

        final Composite recipeTitleComp = new Composite(deleteRecipeShell, SWT.NONE);
        recipeTitleComp.setLayout(new GridLayout(2, false));
        Label recipeTitleLabel = new Label(recipeTitleComp, SWT.NONE);
        final Text recipeTitleText = window.createTextField(recipeTitleComp, recipeTitleLabel,
                "название");

        final Button updateButton = new Button(deleteRecipeShell, SWT.PUSH);
        updateButton.setText("Удалить");
        updateButton.setSize(100, 150);

        updateButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String recipeTitle = recipeTitleText.getText();
                recipeController.deleteRecipe(recipeTitle);
                window.updateShell();
                recipeTitleComp.dispose();
                updateButton.dispose();

                Label sucessLabel = new Label(deleteRecipeShell, SWT.CENTER);
                sucessLabel.setText("Рецепт " + recipeTitle + " успешно удален");

                deleteRecipeShell.layout();
            }
        });

        deleteRecipeShell.setSize(500 , 300);
        deleteRecipeShell.open();
    }

    void addTableListeners(final User user) {
        mainTable.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (user.getUsername() != null) {
                    TableItem item = (TableItem) e.item;
                    if (item == null)
                        return;
                    final String recipeTitle = item.getText(0);
                    window.setSelectedRecipe(recipeController.getRecipeByTitle(recipeTitle));
                }
            }
        });

        mainTable.addListener(SWT.MouseDoubleClick, new Listener() {
            public void handleEvent(Event event) {
                Point pt = new Point(event.x, event.y);
                TableItem item = mainTable.getItem(pt);
                if (item == null)
                    return;
                String recipeTitle = item.getText(0);
                showRecipe(recipeTitle, user);
                mainTable.setSelection(mainTable.getSelectionIndices());
            }
        });

        Listener sortListener = new Listener() {
            public void handleEvent(Event e) {
                TableItem[] items = mainTable.getItems();
                Collator collator = Collator.getInstance(Locale.getDefault());
                TableColumn column = (TableColumn) e.widget;
                int index = 0;
                for (int columnIndex = 0; columnIndex < mainTable.getColumnCount(); columnIndex++) {
                    if (column == mainTable.getColumn(columnIndex)) {
                        index = columnIndex;
                    }
                }
                for (int firstItemIndex = 1; firstItemIndex < items.length; firstItemIndex++) {
                    String firstItemValue = items[firstItemIndex].getText(index);
                    for (int secondItemIndex = 0; secondItemIndex < firstItemIndex; secondItemIndex++) {
                        String secondItemValue = items[secondItemIndex].getText(index);
                        if (collator.compare(firstItemValue, secondItemValue) < 0) {
                            List<String> itemValues = new ArrayList<String>();
                            for (int columnIndex = 0; columnIndex < mainTable.getColumnCount(); columnIndex++) {
                                itemValues.add(items[firstItemIndex].getText(columnIndex));
                            }
                            items[firstItemIndex].dispose();
                            TableItem item = new TableItem(mainTable, SWT.NONE, secondItemIndex);
                            for (int columnIndex = 0; columnIndex < mainTable.getColumnCount(); columnIndex++) {
                                item.setText(columnIndex, itemValues.get(columnIndex));
                            }
                            items = mainTable.getItems();
                            break;
                        }
                    }
                }
                mainTable.setSortColumn(column);
            }
        };

        for (TableColumn column : mainTable.getColumns()) {
            column.addListener(SWT.Selection, sortListener);
        }
    }

    Table getMainTable() {
        return mainTable;
    }
}
