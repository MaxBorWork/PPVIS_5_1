package by.borisevich.book.view;

import by.borisevich.book.user.User;
import by.borisevich.book.user.UserController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

class UserView {

    private MainWindow window;
    private UserController controller = new UserController();

    UserView(MainWindow window) {
        this.window = window;
    }

    void register() {
        final Shell registerShell = new Shell(window.getDisplay(), SWT.DIALOG_TRIM | SWT.V_SCROLL | SWT.H_SCROLL);
        registerShell.setText("Регистрация");
        window.initLayout(registerShell, 1);

        final Composite loginComposite = new Composite(registerShell, SWT.NONE);
        loginComposite.setLayout(new GridLayout(2, false));
        Label loginCompositeLabel = new Label(loginComposite, SWT.NONE);
        final Text loginCompositeText = window.createTextField(loginComposite, loginCompositeLabel,
                "логин");

        final Composite passwordComposite = new Composite(registerShell, SWT.NONE);
        passwordComposite.setLayout(new GridLayout(2, false));
        Label passwordCompositeLabel = new Label(passwordComposite, SWT.NONE);
        final Text passwordCompositeText = window.createTextField(passwordComposite, passwordCompositeLabel,
                "пароль");

        final Composite usernameComposite = new Composite(registerShell, SWT.NONE);
        usernameComposite.setLayout(new GridLayout(2, false));
        Label usernameCompositeLabel = new Label(usernameComposite, SWT.NONE);
        final Text usernameCompositeText = window.createTextField(usernameComposite, usernameCompositeLabel,
                "имя");

        final Button registerButton = new Button(registerShell, SWT.PUSH);
        registerButton.setText("Регистрация");
        registerButton.setSize(100, 150);

        registerButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                User user = new User(loginCompositeText.getText(), passwordCompositeText.getText(),
                                        usernameCompositeText.getText());
                controller.addUser(user);
                loginComposite.dispose();
                passwordComposite.dispose();
                usernameComposite.dispose();

                Label registerSuccessLabel = new Label(registerShell, SWT.NONE);
                registerSuccessLabel.setText("user " + user.getUsername() + "was successfully registered!");

                registerButton.setText("OK");

                registerButton.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        registerShell.close();
                    }
                });
            }
        });

        registerShell.setSize(500 , 600);
        registerShell.open();
    }

    void login(final User user) {
        final Shell loginShell = new Shell(window.getDisplay(), SWT.DIALOG_TRIM | SWT.V_SCROLL | SWT.H_SCROLL);
        loginShell.setText("Регистрация");
        window.initLayout(loginShell, 1);
        User foundUser = new User();
        final Composite loginComposite = new Composite(loginShell, SWT.NONE);
        loginComposite.setLayout(new GridLayout(2, false));
        Label loginCompositeLabel = new Label(loginComposite, SWT.NONE);
        final Text loginCompositeText = window.createTextField(loginComposite, loginCompositeLabel,
                "логин");

        final Composite passwordComposite = new Composite(loginShell, SWT.NONE);
        passwordComposite.setLayout(new GridLayout(2, false));
        Label passwordCompositeLabel = new Label(passwordComposite, SWT.NONE);
        final Text passwordCompositeText = window.createTextField(passwordComposite, passwordCompositeLabel,
                "пароль");
        passwordCompositeText.setEchoChar('*');
        passwordCompositeText.setTextLimit(10);

        final Button loginButton = new Button(loginShell, SWT.PUSH);
        loginButton.setText("Вход");
        loginButton.setSize(100, 150);

        loginShell.setSize(500 , 500);
        loginShell.open();

        loginButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                User foundUser = controller.getUser(loginCompositeText.getText(), passwordCompositeText.getText());

                if (foundUser != null) {
                    user.setId(foundUser.getId());
                    user.setLogin(foundUser.getLogin());
                    user.setPassword(foundUser.getPassword());
                    user.setUsername(foundUser.getUsername());

                    loginComposite.dispose();
                    passwordComposite.dispose();

                    Label loginSuccessLabel = new Label(loginShell, SWT.NONE);
                    loginSuccessLabel.setText("you've logged in successfully!");
                    loginButton.setText("OK");

                    window.updateShell();
                    loginShell.layout();

                    loginButton.addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            loginShell.close();
                        }
                    });
                } else {

                    Label loginErrorLabel = new Label(loginShell, SWT.NONE);
                    loginErrorLabel.setText("Can't log in. Check input data.");

                    loginShell.layout();
                }

            }

        });
    }

}
