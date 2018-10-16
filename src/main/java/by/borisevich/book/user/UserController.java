package by.borisevich.book.user;

public class UserController {

    private UserDao userDao = new UserDao();

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User getUser(String login, String password) {
        return userDao.getUser(login, password);
    }
}
