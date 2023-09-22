package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS User (id BIGINT PRIMARY KEY AUTO_INCREMENT," + "name VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, " + "age TINYINT)";

            session.createNativeQuery(sql, User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception addDeveloper " + e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS User";

            session.createNativeQuery(sql, User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception dropUsersTable " + e);

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);

            session.persist(user);

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception saveUser " + e);

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.remove(user);

            session.flush();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception removeUserById" + e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "FROM jm.task.core.jdbc.model.User";

        List<User> result = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            result = session.createQuery(hql, User.class).list();
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Exception getAllUsers " + e);
            return result;
        }

        return result;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String sql = "DELETE FROM User";

            session.createNativeQuery(sql, User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception cleanUsersTable" + e);

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
