package com.shervinzadsoroor.repositories.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.models.CreditCard;
import com.shervinzadsoroor.repositories.interfaces.Validations;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class ValidationImpl implements Validations {
    @Override
    public boolean isCardToCardInfoValid(String info) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        JsonMapper mapper = new JsonMapper();
        Map map = mapper.readValue(info, Map.class);

        boolean isInfoValid = false;
        boolean isSourceCardExist = false;
        boolean isAmountValid = false;
        boolean isDestinationCardExist = false;


        Long sourceCardNumber = (Long) map.get("sourceNumber");
        Long amount = Long.parseLong(map.get("amount") + "");
        Long destinationCardNumber = (Long) map.get("destination");


        List<Account> accountList;

        List<CreditCard> sourceCard = session.createQuery("from CreditCard where cardNumber=:number")
                .setParameter("number", sourceCardNumber + "")
                .list();
        if (sourceCard.size() > 0) {
            isSourceCardExist = true;


            accountList = session.createQuery("from Account where creditCard=:creditCard")
                    .setParameter("creditCard", sourceCard.get(0))
                    .list();
            Long balance = accountList.get(0).getBalance();
            if (amount <= (balance - 5000)) {
                isAmountValid = true;
            }
        }

        List<CreditCard> destinationCard = session.createQuery("from CreditCard where cardNumber=:number")
                .setParameter("number", destinationCardNumber + "")
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
    public boolean isPasswordValid(int password, String info) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        boolean isPasswordValid = false;

        JsonMapper mapper = new JsonMapper();
        Map map = mapper.readValue(info,Map.class);
        Long sourceCardNumber = (Long) map.get("sourceNumber");

        List<CreditCard> sourceCardList = session.createQuery("from CreditCard where cardNumber=:number")
                .setParameter("number", sourceCardNumber + "")
                .list();
        CreditCard creditCard = sourceCardList.get(0);

        List<Account> accountList = session.createQuery("from Account where creditCard=:card")
                .setParameter("card", creditCard)
                .list();
        Account account = accountList.get(0);

        int sourceCardPass = sourceCardList.get(0).getFirstPass();
        if (password == sourceCardPass) {
            isPasswordValid = true;
        } else {
            Query query = session.createQuery("update Account set numOfWrongPassEntered=:num" +
                    " where id=:id")
                    .setParameter("num", (account.getNumOfWrongPassEntered() + 1))
                    .setParameter("id", account.getId());
            query.executeUpdate();

            session.refresh(account);
            if (account.getNumOfWrongPassEntered() == 3) {
                Query query1 = session.createQuery("update Account set isActive=false" +
                        " where id=:id")
                        .setParameter("id", account.getId());
                query1.executeUpdate();
            }
        }

        session.getTransaction().commit();
        session.close();
        return isPasswordValid;
    }

    @Override
    public boolean isAccountActive(String info) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        JsonMapper mapper = new JsonMapper();
        Map map = mapper.readValue(info, Map.class);
        Long creditCardNumber = (Long) map.get("sourceNumber");

        List<CreditCard> sourceCardList = session.createQuery("from CreditCard where cardNumber=:number")
                .setParameter("number", creditCardNumber + "")
                .list();
        CreditCard creditCard = sourceCardList.get(0);

        List<Account> accountList = session.createQuery("from Account where creditCard=:creditCard")
                .setParameter("creditCard", creditCard)
                .list();
        Account account = accountList.get(0);

        session.getTransaction().commit();
        session.close();
        return account.isActive();
    }
}
