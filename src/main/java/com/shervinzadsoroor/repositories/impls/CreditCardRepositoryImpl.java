package com.shervinzadsoroor.repositories.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.models.CreditCard;
import com.shervinzadsoroor.models.Transaction;
import com.shervinzadsoroor.repositories.interfaces.CreditCardRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class CreditCardRepositoryImpl implements CreditCardRepository {
    @Override
    public void create() {

    }

    @Override
    public CreditCard retrieve(Long cardId) {
        return null;
    }

    @Override
    public void update(Long cardId) {

    }

    @Override
    public void delete(Long cardId) {

    }

    @Override
    public void changeFirstPass(String newPassStr) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        JsonMapper mapper = new JsonMapper();
        Map map = mapper.readValue(newPassStr, Map.class);

        Long accountId = Long.parseLong(map.get("id") + "");
        int newPass = (Integer) (map.get("pass"));

        Account account = session.load(Account.class, accountId);
        Long cardId = account.getCreditCard().getId();

        CreditCard creditCard = session.load(CreditCard.class, cardId);
        creditCard.setFirstPass(newPass);

        session.update(creditCard);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void assignSecondPass(String idStr) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        JsonMapper mapper = new JsonMapper();
        Map map = mapper.readValue(idStr, Map.class);

        Long accountId = Long.parseLong(map.get("id") + "");

        Account account = session.load(Account.class, accountId);
        Long cardId = account.getCreditCard().getId();

        CreditCard creditCard = session.load(CreditCard.class, cardId);
        String secondPass = creditCard.getSecondPass();
        if (secondPass == null) {
            System.out.println("enter second password: ");
            String pass1 = scanner.nextLine();
            System.out.println("enter second password again:");
            String pass2 = scanner.nextLine();
            if (pass1.equals(pass2)) {
                creditCard.setSecondPass(pass1);
                session.update(creditCard);
            }
        } else {
            System.out.println("your account has already a second password !");
        }

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void editSecondPass(String jsonSecondPass) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        JsonMapper mapper = new JsonMapper();
        Map map = mapper.readValue(jsonSecondPass, Map.class);

        Long accountId = Long.parseLong(map.get("id") + "");
        String secondPass = map.get("pass") + "";

        Account account = session.load(Account.class, accountId);
        Long cardId = account.getCreditCard().getId();

        CreditCard creditCard = session.load(CreditCard.class, cardId);
        creditCard.setSecondPass(secondPass);

        session.update(creditCard);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void cardToCard(String info) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Account sourceAccount = null;
        Account destinationAccount = null;

        JsonMapper mapper = new JsonMapper();
        Map map = mapper.readValue(info, Map.class);
        Long sourceCardNumber = (Long) map.get("sourceNumber");
        Long amount = Long.parseLong(map.get("amount") + "");
        Long destinationCardNumber = (Long) map.get("destination");

        List<CreditCard> sourceCardList = session.createQuery("from CreditCard where cardNumber=:number")
                .setParameter("number", sourceCardNumber + "")
                .list();

        Long sourceCardId = sourceCardList.get(0).getId();
        CreditCard sourceCard = session.load(CreditCard.class, sourceCardId);

        List<Account> sourceAccountList = session.createQuery("from Account where creditCard=:number")
                .setParameter("number", sourceCard)
                .list();

        sourceAccount = sourceAccountList.get(0);

        //=======================================================================================

        List<CreditCard> destinationCardList = session.createQuery("from CreditCard where cardNumber=:number")
                .setParameter("number", destinationCardNumber + "")
                .list();

        Long destinationCardId = destinationCardList.get(0).getId();
        CreditCard destinationCard = session.load(CreditCard.class, destinationCardId);

        List<Account> destinationAccountList = session.createQuery("from Account where creditCard=:number")
                .setParameter("number", destinationCard)
                .list();

        destinationAccount = destinationAccountList.get(0);

        //========================================================================================

        String destinationAccountCostumerName = destinationAccount.getCostumer().getFirstName()
                + " " + destinationAccount.getCostumer().getLastName();

        String sourceAccountCostumerName = sourceAccount.getCostumer().getFirstName() +
                " " + sourceAccount.getCostumer().getLastName();

        //========================================================================

        Long sourceAccountId = sourceAccount.getId();
        Long destinationAccountId = destinationAccount.getId();

        Query sourceQuery = session.createQuery("update Account set balance=:newBalance where id=" + sourceAccountId)
                .setParameter("newBalance", (sourceAccount.getBalance() - (amount + 5000L)));

        sourceQuery.executeUpdate();

        Query destinationQuery = session.createQuery("update Account set balance=:newBalance where id=" + destinationAccountId)
                .setParameter("newBalance", (destinationAccount.getBalance() + amount));

        destinationQuery.executeUpdate();


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateAndTime = dtf.format(now);


        String sourceTransactionDescription = "amount " + (amount + 5000L) + " rials transferred to costumer "
                + destinationAccountCostumerName + " with credit card number " + destinationCardNumber
                + " in " + dateAndTime;

        String destinationTransactionDescription = "amount " + amount + " rials was deposited from costumer "
                + sourceAccountCostumerName + " with credit card number " + sourceCardNumber
                + " in " + dateAndTime;

        String date = LocalDate.now().toString();

        Transaction sourceTransaction = new Transaction(null, (amount + 5000L), date,
                sourceTransactionDescription, true, sourceAccount);

        Transaction destinationTransaction = new Transaction(amount, null, date,
                destinationTransactionDescription, true, destinationAccount);

        session.save(sourceTransaction);
        session.save(destinationTransaction);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public String cardToCardInfo() throws JsonProcessingException {
        Scanner scanner = new Scanner(System.in);
        JsonMapper mapper = new JsonMapper();
        Map<String, Long> infoMapper = new HashMap<>();

        System.out.println("enter source card number: ");
        Long sourceNumber = scanner.nextLong();

        System.out.println("enter amount(Rials): ");
        Long amount = 0L;
        while (true) {
            amount = scanner.nextLong();
            if (amount <= 30_000_000 && amount > 0) {
                break;
            } else {
                System.out.println("invalid amount!!!(enter 1 to 30'000'000 rials): ");
            }
        }
        System.out.println("enter destination card number: ");
        Long destinationNumber = scanner.nextLong();

        infoMapper.put("sourceNumber", sourceNumber);
        infoMapper.put("amount", amount);
        infoMapper.put("destination", destinationNumber);

        String JsonString = mapper.writeValueAsString(infoMapper);

        String cardsInto = "{\"source\":123,\"amount\":23,\"destination\":234324}";
        return JsonString;
    }

    @Override
    public boolean isPasswordValid(String idAndPass) throws JsonProcessingException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        JsonMapper mapper = new JsonMapper();
        Map map = mapper.readValue(idAndPass, Map.class);

        Long accountId = Long.parseLong(map.get("id") + "");
        Long password = Long.parseLong(map.get("pass") + "");

        boolean isPassValid = false;
        List<Account> accounts = session.createQuery("from Account where id=:id")
                .setParameter("id", accountId)
                .list();
        if (accounts.size() > 0) {
            Account account = accounts.get(0);
            if (account.getCreditCard().getFirstPass() == password) {
                isPassValid = true;
            }
        }

        session.getTransaction().commit();
        session.close();
        return isPassValid;
    }
}
