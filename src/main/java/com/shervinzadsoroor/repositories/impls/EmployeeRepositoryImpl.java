package com.shervinzadsoroor.repositories.impls;

import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.repositories.interfaces.EmployeeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public void activateTheAccount(Long inactiveAccountId, List<Account> deactiveAccounts) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (Account deactiveAccount : deactiveAccounts) {
            if (deactiveAccount.getId() == inactiveAccountId) {
                Account account =session.load(Account.class,inactiveAccountId);
                account.setNumOfWrongPassEntered(0);
                account.setActive(true);
                session.update(account);
            }else {
                System.out.println("id not exist or not deactive !!!");
            }
        }

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteAccount(Long accountId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Account> accountList = session.createQuery("from Account where id=:id")
                .setParameter("id", accountId)
                .list();
        if (accountList.size() > 0) {
            Account account = session.load(Account.class, accountId);
            session.delete(account);
        } else {
            System.out.println("account not exist !!!");
        }

        session.getTransaction().commit();
        session.close();
    }
}
