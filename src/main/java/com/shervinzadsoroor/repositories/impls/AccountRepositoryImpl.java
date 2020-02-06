package com.shervinzadsoroor.repositories.impls;

import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.models.Costumer;
import com.shervinzadsoroor.repositories.interfaces.AccountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public void create() {

    }

    @Override
    public Account retrieve(Long accountId) {
        return null;
    }

    @Override
    public void update(Long accountId) {

    }

    @Override
    public void delete(Long accountId) {

    }

    @Override
    public List<Account> findAll(Long costumerId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        boolean isIsExist = false;

        List<Costumer> costumers = session.createQuery("from Costumer ")
                .list();
        for (Costumer costumer : costumers) {
            if (costumer.getId()==costumerId){
                isIsExist = true;
            }
        }

        List<Account> accounts = new ArrayList<>();
        if (isIsExist) {
            Costumer costumer = session.load(Costumer.class, costumerId);
            accounts = session.createQuery("from Account where costumer=:costumer")
                    .setParameter("costumer", costumer)
                    .list();
        }

        session.getTransaction().commit();
        session.close();
        return accounts;
    }

    @Override
    public void deactivate(Long accountId) {

    }
}
