package com.shervinzadsoroor.repositories;

import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateInit {
    public static void init() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        System.out.println("\t\t******  welcome to banking application   ******");

        session.getTransaction().commit();
        session.close();
    }
}
