package by.borisevich.book.user;

import by.borisevich.book.util.HibernateSessionFactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    private SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    void addUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        logger.info("user " + user.getUsername() + " added successfully");
    }

    User getUser(String login, String password) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM User WHERE login = :login AND password = :password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        User user = (User) query.uniqueResult();
        if (user == null) {
            logger.info("can't find user " + login);
            session.close();
            return null;
        } else {
            session.close();
            return user;
        }
    }
}
