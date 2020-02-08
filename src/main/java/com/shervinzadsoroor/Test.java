package com.shervinzadsoroor;

import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.models.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {

//        long millis=System.currentTimeMillis();
//        java.sql.Date date=new java.sql.Date(millis);
//        System.out.println(millis);
//        System.out.println(date);


        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Account account = session.load(Account.class, 1L);

        List<Transaction> transactions = account.getTransactions();
        List<Transaction> filteredTransactions = new ArrayList<>();

        String dateStr = "2020-01-02";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);

        LocalDate date1;
        for (Transaction t : transactions) {
            date1 = LocalDate.parse(t.getDate(),formatter);
            if (date1.isAfter(date)){
                filteredTransactions.add(t);
            }
        }
        for (Transaction filtered : filteredTransactions) {
            System.out.println(filtered.toString());
        }

        // transactions.stream().filter(transaction -> transaction)
        session.getTransaction().commit();
        session.close();

    }

}

