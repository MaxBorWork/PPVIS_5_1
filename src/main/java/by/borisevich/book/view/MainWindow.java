package by.borisevich.book.view;

import by.borisevich.book.user.User;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class MainWindow {

    private Display display = new Display();;
    private Shell shell;
    private Menu mainMenu;
    private RecipeView recipeView = new RecipeView(this);
    private UserView userView = new UserView(this);
    private User user = new User();

    public MainWindow() {
        shell = new Shell(display);
        shell.setText("Кулинарная книга");
        shell.setSize(820, 600);

        initLayout(shell, 1);
        initMenuBar();
        recipeView.initTable();
        recipeView.showRecipes(user);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    void initLayout(Shell shell, int numColumns) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.makeColumnsEqualWidth = false;
        gridLayout.numColumns = numColumns;
        shell.setLayout(gridLayout);
    }

    private void initMenuBar() {
        mainMenu = new Menu(shell, SWT.BAR);

        final MenuItem recipeItem = new MenuItem(mainMenu, SWT.CASCADE);
        recipeItem.setText("Рецепт");

        Menu recipeSubeMenu = new Menu(shell, SWT.DROP_DOWN);
        recipeItem.setMenu(recipeSubeMenu);

        MenuItem addRecipeItem = new MenuItem(recipeSubeMenu, SWT.PUSH);
        addRecipeItem.setText("Добавить");
        addRecipeItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                recipeView.addRecipe();
            }
        });

        MenuItem searchRecipeItem = new MenuItem(recipeSubeMenu, SWT.PUSH);
        searchRecipeItem.setText("Найти");
        searchRecipeItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                recipeView.findRecipe(user);
            }
        });

        MenuItem editRecipeItem = new MenuItem(recipeSubeMenu, SWT.PUSH);
        editRecipeItem.setText("Изменить");


        MenuItem deleteRecipeItem = new MenuItem(recipeSubeMenu, SWT.PUSH);
        deleteRecipeItem.setText("Удалить");
        deleteRecipeItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                recipeView.deleteRecipe();
            }
        });

        MenuItem userItem = new MenuItem(mainMenu, SWT.CASCADE);
        userItem.setText("Пользователь");

        Menu userSubMenu = new Menu(shell, SWT.DROP_DOWN);
        userItem.setMenu(userSubMenu);

        MenuItem registerItem = new MenuItem(userSubMenu, SWT.PUSH);
        registerItem.setText("Регистрация");
        registerItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                userView.register();
            }
        });

        MenuItem loginItem = new MenuItem(userSubMenu, SWT.PUSH);
        loginItem.setText("Вход");

        final MenuItem logoutItem = new MenuItem(userSubMenu, SWT.PUSH);
        logoutItem.setText("Выход");
        logoutItem.setEnabled(false);
        logoutItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                user = new User();
                updateShell();
                logoutItem.setEnabled(false);
            }
        });

        loginItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                userView.login(user);
                logoutItem.setEnabled(true);
            }
        });

        shell.setMenuBar(mainMenu);
    }

    Text createTextField(Composite compositeName, Label labelName, String text) {
        labelName.setText("Введите " + text + ": ");

        Text textName = new Text(compositeName, SWT.NONE);
        textName.setLayoutData(new GridData(300, 100));

        return textName;
    }

    void updateShell() {
        recipeView.getMainTable().removeAll();
        recipeView.showRecipes(user);
        shell.layout();
    }

    Display getDisplay() {
        return display;
    }

    Shell getShell() {
        return shell;
    }

    public Menu getMainMenu() {
        return mainMenu;
    }
}
