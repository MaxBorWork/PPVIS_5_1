package by.borisevich.book.recipe;

import by.borisevich.book.util.HibernateSessionFactoryUtil;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RecipeDao {

    private static final Logger logger = LoggerFactory.getLogger(RecipeDao.class);
    private SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    void addRecipe(Recipe recipe) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(recipe);
        session.getTransaction().commit();
        session.close();
        logger.info("recipe " + recipe.getTitle() + " added successfully");
    }

    List<Recipe> listRecipe() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Recipe");
        List<Recipe> recipes = (List<Recipe>) query.list();
        for (Recipe recipe : recipes) {
            Hibernate.initialize(recipe.getIngredientsMap());
        }
        session.close();
        if (recipes.size() == 0) {
            logger.info("request set is empty");
            return null;
        }
        return recipes;
    }

    Recipe getRecipeByTitle(String title) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Recipe where title = :paramName");
        query.setParameter("paramName", title);
        Recipe recipe = (Recipe) query.uniqueResult();
        if (recipe == null) {
            logger.info("request set is empty");
            session.close();
            return null;
        } else {
            Hibernate.initialize(recipe.getIngredientsMap());
            session.close();
            return recipe;
        }
    }

    void updateRecipe(Recipe recipe) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(recipe);
        session.getTransaction().commit();
        session.close();
        logger.info("recipe " + recipe.getTitle() + " updated successfully");
    }

    void deleteRecipe(String title) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Recipe where title = :paramName");
        query.setParameter("paramName", title);
        List<Recipe> recipeList = (List<Recipe>) query.list();
        if (recipeList.size() == 0) {
            logger.info("request set is empty");
            return;
        }
        session.beginTransaction();
        session.delete(recipeList.get(0));
        logger.info("were deleted recipe with motorNumber: " + title);
        session.getTransaction().commit();
        session.close();
        logger.info("recipe " + recipeList.get(0).getTitle() + " deleted successfully");
    }
}
