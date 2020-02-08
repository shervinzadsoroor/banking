package com.shervinzadsoroor.repositories.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.models.Transaction;
import com.shervinzadsoroor.repositories.interfaces.TransactionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransactionRepositoryImpl implements TransactionRepository {
    @Override
    public List<Transaction> find(Long accountId, String date) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

// TODO: 2/7/20

        session.getTransaction().commit();
        session.close();
        return null;
    }

    @Override
    public List<Transaction> findByAccountId(Long accountId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Account account = session.load(Account.class, accountId);
        List<Transaction> transactions = session.createQuery("from Transaction where account=:account")
                .setParameter("account", account)
                .list();

        session.getTransaction().commit();
        session.close();

        return transactions;
    }

    @Override
    public void printTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            System.out.println(transaction.toString());
        }
    }

    @Override
    public List<Transaction> findByDateAndAccountId(Long accountId,String strDate) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

//        JsonMapper mapper = new JsonMapper();
//        Map map = mapper.readValue(jsonSearchTransaction, Map.class);

//        Long accountId = Long.parseLong(map.get("id") + "");
//        String strDate = map.get("date") + "";


        Account account = session.load(Account.class, accountId);

        List<Transaction> transactions = account.getTransactions();

        List<Transaction> filteredTransactions = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(strDate, formatter);

        LocalDate databaseDate;
        for (Transaction t : transactions) {
            databaseDate = LocalDate.parse(t.getDate(), formatter);
            if (databaseDate.isAfter(date)) {
                filteredTransactions.add(t);
            }
        }

        session.getTransaction().commit();
        session.close();
        return filteredTransactions;
    }
}
