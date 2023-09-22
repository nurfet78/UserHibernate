package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/databasetest")
                    .setProperty("hibernate.connection.username", "root")
                    .setProperty("hibernate.connection.password", "root")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory();

        } catch (Throwable e) {
            System.out.println("Ошибка конфигурации " + e);
        }

        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}
