package com.shervinzadsoroor.repositories;

import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Branch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.Query;

public class HibernateInit {
    public static void init() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        System.out.println("\t\t******  welcome to banking application   ******");

        boolean isEmpty = session.createQuery("from Branch").list().isEmpty();

        if (isEmpty) {
            ApplicationContext context =
                    new ClassPathXmlApplicationContext("beans.xml");
            Branch branch1 = context.getBean("branch1", Branch.class);
            session.save(branch1);

            Branch branch2 = context.getBean("branch2", Branch.class);
            session.save(branch2);
        }

        session.getTransaction().commit();
        session.close();
    }
}
