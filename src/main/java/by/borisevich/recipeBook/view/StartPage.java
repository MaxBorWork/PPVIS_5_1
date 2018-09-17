package by.borisevich.recipeBook.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class StartPage {

    private Display display;
    private Shell shell;

    public StartPage() {
        display = new Display();
        shell = new Shell(display);
        shell.setText("Кулинарная книга");
        shell.setSize(1400, 900);

        initLayout();
        initMenuBar();

        initTable();


        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    public void initLayout() {
        GridLayout gridLayout = new GridLayout();
        gridLayout.makeColumnsEqualWidth = true;
        gridLayout.numColumns = 5;
        shell.setLayout(gridLayout);
    }

    public void initMenuBar() {
        Menu mainMenu = new Menu(shell, SWT.BAR);

        MenuItem findRecipeItem = new MenuItem(mainMenu, SWT.PUSH);
        findRecipeItem.setText("Поиск");

        MenuItem registerItem = new MenuItem(mainMenu, SWT.PUSH);
        registerItem.setText("Регистрация");

        MenuItem loginItem = new MenuItem(mainMenu, SWT.PUSH);
        loginItem.setText("Вход");

        shell.setMenuBar(mainMenu);
    }

    public void updateShell() {

    }

    public void initTable() {
        Table mainTable = new Table(shell, SWT.MULTI | SWT.BORDER |
                SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
        mainTable.setHeaderVisible(true);
        mainTable.setLinesVisible(true);

        TableColumn titleColumn = new TableColumn(mainTable, SWT.NONE);
        titleColumn.setText("Название");
        titleColumn.setWidth(150);

        TableColumn urlColumn = new TableColumn(mainTable, SWT.NONE);
        urlColumn.setText("URL");
        titleColumn.setWidth(250);

        TableColumn ingredientsColumn = new TableColumn(mainTable, SWT.NONE);
        ingredientsColumn.setText("Ингридиенты");
        titleColumn.setWidth(250);

        TableColumn cookingTimeColumn = new TableColumn(mainTable, SWT.NONE);
        cookingTimeColumn.setText("Время приготовления");
        titleColumn.setWidth(150);

        mainTable.setLayoutData(new GridData(500, 400));

    }
}
