package com.shervinzadsoroor.repositories.impls;

import com.shervinzadsoroor.hibernateConfig.HibernateUtil;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.models.CreditCard;
import com.shervinzadsoroor.models.Transaction;
import com.shervinzadsoroor.repositories.interfaces.CreditCardRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

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
    public void editFirstPass(Long cardId, Long newPass) {

    }

    @Override
    public void assignSecondPass(Long cardId, Long secondPass) {

    }

    @Override
    public void editSecondPass(Long cardId, Long newPass) {

    }

    @Override
    public void cardToCard(Long[] info) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Account sourceAccount = null;
        Account destinationAccount = null;
        Long sourceCardNumber = info[0];
        Long amount = info[1];
        Long destinationCardNumber = info[2];

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

        Transaction sourceTransaction = new Transaction(null, (amount + 5000L), null,
                sourceTransactionDescription, true, sourceAccount);

        Transaction destinationTransaction = new Transaction(amount, null, null,
                destinationTransactionDescription, true, destinationAccount);

        session.save(sourceTransaction);
        session.save(destinationTransaction);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Long[] cardToCardInfo() {
        Scanner scanner = new Scanner(System.in);
        Long[] info = new Long[3];

        System.out.println("enter source card number: ");
        info[0] = scanner.nextLong();

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
        info[1] = amount;
        System.out.println("enter destination card number: ");
        info[2] = scanner.nextLong();

        return info;
    }
}
