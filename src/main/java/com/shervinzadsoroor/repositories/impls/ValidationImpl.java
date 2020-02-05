package com.shervinzadsoroor.repositories.impls;

import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.models.CreditCard;
import com.shervinzadsoroor.repositories.interfaces.Validations;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ValidationImpl implements Validations {
    @Override
    public boolean isCardToCardInfoValid(Long[] info) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        boolean isInfoValid = false;

        boolean isSourceCardExist = false;
        boolean isAmountValid = false;
        boolean isDestinationCardExist = false;

        Long sourceCardNumber = info[0];
        Long amount = info[1];
        Long destinationCardNumber = info[2];

        Long sourceCardId = null;
        List<Account> accountList;

        List<CreditCard> sourceCard = session.createQuery("from CreditCard where cardNumber=:number")
                .setParameter("number", sourceCardNumber+"")
                .list();
        if (sourceCard.size() > 0) {
            isSourceCardExist = true;
           // sourceCardId = sourceCard.get(0).getId();

            accountList = session.createQuery("from Account where creditCard=:creditCard")
                    .setParameter("creditCard", sourceCard)
                    .list();
            Long balance = accountList.get(0).getBalance();
            if (amount <= (balance - 5000)) {
                isAmountValid = true;
            }
        }

        List<CreditCard> destinationCard = session.createQuery("from CreditCard where cardNumber=:number")
                .setParameter("number", destinationCardNumber+"")
                .list();
        if (destinationCard.size() > 0) {
            isDestinationCardExist = true;
        }

        if (isSourceCardExist && isDestinationCardExist && isAmountValid) {
            isInfoValid = true;
        }

        session.getTransaction().commit();
        session.close();
        return isInfoValid;
    }

    @Override
    public boolean isPasswordValid(int password, Long sourceCardNumber) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        boolean isPasswordValid = false;

        List<CreditCard> sourceCardList = session.createQuery("from CreditCard where cardNumber=:number")
                .setParameter("number", sourceCardNumber+"")
                .list();
        int sourceCardPass = sourceCardList.get(0).getFirstPass();
        if (password == sourceCardPass) {
            isPasswordValid = true;
        }

        session.getTransaction().commit();
        session.close();
        return isPasswordValid;
    }
}
