package com.shervinzadsoroor.repositories.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.models.Costumer;
import com.shervinzadsoroor.models.CreditCard;
import com.shervinzadsoroor.models.Transaction;
import com.shervinzadsoroor.repositories.interfaces.AccountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.*;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public Long create(String info) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        JsonMapper mapper = new JsonMapper();
        Map map = mapper.readValue(info, Map.class);

        Long costumerId = Long.parseLong(map.get("costumerId") + "");
        Long balance = Long.parseLong(map.get("balance") + "");

        String cardNumber = generateCreditCardNumber();
        int CVV2 = generateCVV2();


        String expireDate = LocalDate.now().plusYears(3L).toString();

        Costumer costumer = session.load(Costumer.class, costumerId);

        CreditCard creditCard = new CreditCard(cardNumber, CVV2, expireDate);
        session.save(creditCard);
        session.refresh(creditCard);

        Account account = new Account(balance, costumer, creditCard);
        session.save(account);
        session.refresh(account);


        String date = LocalDate.now().toString();

        Transaction transaction = new Transaction(balance, null, date,
                "first deposit", true, account);
        session.save(transaction);

        session.getTransaction().commit();
        session.close();
        return account.getId();
    }

    @Override
    public String generateCreditCardNumber() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String cardNumber = null;
        while (true) {
            int number = 10000000 + new Random().nextInt(90000000);
            String n = "61043376";
            cardNumber = n + number;

            List<CreditCard> creditCards = session.createQuery("from CreditCard where cardNumber=:number")
                    .setParameter("number", cardNumber)
                    .list();

            if (creditCards.size() == 0) {
                break;
            }
        }
        session.getTransaction().commit();
        session.close();
        return cardNumber;
    }

    @Override
    public int generateCVV2() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int CVV2;
        while (true) {
            CVV2 = 1000 + new Random().nextInt(9000);

            List<CreditCard> CVV2s = session.createQuery("from CreditCard where CVV2=:number")
                    .setParameter("number", CVV2)
                    .list();

            if (CVV2s.size() == 0) {
                break;
            }
        }
        session.getTransaction().commit();
        session.close();
        return CVV2;
    }

    @Override
    public String getCreateAccountInfo() throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Scanner scanner = new Scanner(System.in);

        System.out.println("enter your costumer id: ");
        Long costumerId;
        while (true) {
            costumerId = scanner.nextLong();

            List<Costumer> costumers = session.createQuery("from Costumer where id=:id")
                    .setParameter("id", costumerId)
                    .list();
            if (costumers.size() > 0) {
                break;
            } else {
                System.out.println("costumer id not found !!! , please enter a valid id: ");
            }
        }
        System.out.println("enter your initial balance: ");
        Long balance;
        while (true) {
            balance = scanner.nextLong();
            if (balance >= 100000) {
                break;
            } else {
                System.out.println("invalid balance, enter an amount higher than 100000 rials: ");
            }
        }

        JsonMapper mapper = new JsonMapper();
        Map<String, Long> map = new HashMap<>();
        map.put("costumerId", costumerId);
        map.put("balance", balance);
        String info = mapper.writeValueAsString(map);

        session.getTransaction().commit();
        session.close();

        return info;
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
    public List<Account> findAll(String id) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        boolean isIsExist = false;
        JsonMapper mapper = new JsonMapper();
        Map map = mapper.readValue(id, Map.class);
        Long costumerId = Long.parseLong(map.get("costumerId") + "");

        List<Costumer> costumers = session.createQuery("from Costumer ")
                .list();
        for (Costumer costumer : costumers) {
            if (costumer.getId() == costumerId) {
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
