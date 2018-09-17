package by.borisevich.recipeBook.dao;

import by.borisevich.recipeBook.model.User;

public interface UserDao {

    public void addUser(User user);

    public User getUser(String login, String password);
}
