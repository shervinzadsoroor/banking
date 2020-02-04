package com.shervinzadsoroor.hibernateConfig;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static SessionFactory sessionFactoryH2;

    static {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        sessionFactoryH2 = new Configuration().configure("hibernate.h2.cfg.xml").buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionFactory getSessionFactoryH2() {
        return sessionFactoryH2;
    }
}
