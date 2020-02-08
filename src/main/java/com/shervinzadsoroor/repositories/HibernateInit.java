package com.shervinzadsoroor.repositories;

import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.models.Branch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

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
        System.out.print("+---------------------------------+\n" +
                "|  1  -->    card to card         |\n" +
                "|  2  -->    show accounts        |\n" +
                "|  3  -->    create account       |\n" +
                "|  4  -->    withdraw             |\n" +
                "|  5  -->    deposit              |\n" +
                "|  6  -->    show balance         |\n" +
                "|  7  -->    show transactions    |\n" +
                "|  8  -->    password operations  |\n" +
                "|  9  -->    admin operations     |\n" +
                "+---------------------------------+\n\n");
    }

    public void showAdminServices() {
        System.out.print("+---------------------------------+\n" +
                "|  1  -->    activate accounts    |\n" +
                "|  2  -->    delete account       |\n" +
                "+---------------------------------+\n\n");
    }

    public void showPasswordServices() {
        System.out.print("+-------------------------------------+\n" +
                "|  1  -->    change first password    |\n" +
                "|  2  -->    assign second password   |\n" +
                "|  3  -->    change second password   |\n" +
                "+-------------------------------------+\n\n");
    }

    public List<Account> showDeactiveAccounts(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Account> accountList = session.createQuery("from Account where isActive=false")
                .list();

        for (Account account : accountList) {
            System.out.println(account.toString());
        }

        session.getTransaction().commit();
        session.close();
        return accountList;
    }

    public void showTransactionServices() {
        System.out.print("+---------------------------------+\n" +
                "|  1  -->    all transactions     |\n" +
                "|  2  -->    search by date       |\n" +
                "+---------------------------------+\n\n");
    }

}
