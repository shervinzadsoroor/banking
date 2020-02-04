package com.shervinzadsoroor.repositories;

import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Branch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HibernateInit {
    public static void init() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        System.out.println("\t\t******  welcome to banking application   ******");

        ApplicationContext context =
                new ClassPathXmlApplicationContext("beans.xml");
        Branch branch1 = context.getBean("branch1", Branch.class);
//        System.out.println(branch1.getName());
//        System.out.println(branch1.getManager().toString());
        session.save(branch1);

        session.getTransaction().commit();
        session.close();
    }
}
