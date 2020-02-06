package com.shervinzadsoroor.repositories;

import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Branch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HibernateInit {
    public void init() {
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

    public void showServices() {
        System.out.printf("+---------------------------------+\n" +
                "|  1  -->    card to card         |\n" +
                "|  2  -->    show accounts        |\n" +
                "|  3  -->    withdraw             |\n" +
                "|  4  -->    deposit              |\n" +
                "|  5  -->    show balance         |\n" +
                "|  6  -->    password operations  |\n" +
                "|  7  -->    admin operations     |\n" +
                "+---------------------------------+\n\n");
    }

}
