package by.borisevich.recipeBook.dao.impl;

import by.borisevich.recipeBook.dao.RecipeDao;
import by.borisevich.recipeBook.model.Recipe;
import by.borisevich.recipeBook.util.HibernateSessionFactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RecipeDaoImpl implements RecipeDao {

    private static final Logger logger = LoggerFactory.getLogger(RecipeDaoImpl.class);
    private SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    public void addRecipe(Recipe recipe) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(recipe);
        session.getTransaction().commit();
        session.close();
        logger.info("recipe " + recipe.getTitle() + " added successfully");
    }

    public List<Recipe> listRecipe() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Recipe");
        List<Recipe> recipes = (List<Recipe>) query.list();
        session.close();
        if (recipes.size() == 0) {
            logger.info("request set is empty");
            return null;
        }
        return recipes;
    }

    public Recipe getRecipeByTitle(String title) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Recipe where title = :paramName");
        query.setParameter("paramName", title);
        List recipes = (List<Recipe>) query.list();
        session.close();
        if (recipes.size() == 0) {
            logger.info("request set is empty");
            return null;
        }
        return (Recipe) recipes.get(0);
    }

    public void updateRecipe(Recipe recipe) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(recipe);
        session.getTransaction().commit();
        session.close();
        logger.info("recipe " + recipe.getTitle() + " updated successfully");
    }

    public void deleteRecipe(String title) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Recipe where title = :paramName");
        query.setParameter("paramName", title);
        List<Recipe> recipes = (List<Recipe>) query.list();
        if (recipes.size() == 0) {
            logger.info("request set is empty");
            return;
        }
        session.beginTransaction();
        session.delete(recipes.get(0));
        logger.info("were deleted recipe with motorNumber: " + title);
        session.getTransaction().commit();
        session.close();
        logger.info("recipe " + recipes.get(0).getTitle() + " deleted successfully");
    }
}
