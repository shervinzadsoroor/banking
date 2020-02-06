package com.shervinzadsoroor;

import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.repositories.HibernateInit;
import com.shervinzadsoroor.repositories.impls.AccountRepositoryImpl;
import com.shervinzadsoroor.repositories.impls.CreditCardRepositoryImpl;
import com.shervinzadsoroor.repositories.impls.ValidationImpl;
import com.shervinzadsoroor.repositories.interfaces.AccountRepository;
import com.shervinzadsoroor.repositories.interfaces.CreditCardRepository;
import com.shervinzadsoroor.repositories.interfaces.Validations;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        HibernateInit hibernateInit = new HibernateInit();
        hibernateInit.init();
        Scanner scanner = new Scanner(System.in);
        Scanner longScanner = new Scanner(System.in);
        String command = null;

        CreditCardRepository creditCardRepository =
                new CreditCardRepositoryImpl();

        AccountRepository accountRepository =
                new AccountRepositoryImpl();

        Validations validation = new ValidationImpl();

        while (true) {
            hibernateInit.showServices();
            System.out.println("choose your service please : ");
            command = scanner.nextLine();

            if (command.equals("1")) {
                Long[] info = creditCardRepository.cardToCardInfo();

                boolean isInfoValid = validation.isCardToCardInfoValid(info);
                boolean isAccountActive = false;
                if (isInfoValid) {
                    isAccountActive = validation.isAccountActive(info[0]);
                }
                boolean isPasswordValid = false;

                if (isInfoValid && isAccountActive) {
                    System.out.println("info is valid, please enter your password: ");
                    int password = Integer.parseInt(scanner.nextLine());
                    Long sourceCardNumber = info[0];
                    isPasswordValid = validation.isPasswordValid(password, sourceCardNumber);
                } else if (!isInfoValid) {
                    System.out.println("invalid cards info!!!");
                } else {
                    System.out.println("your account is DEACTIVE !!!");
                }
                if (isPasswordValid) {
                    creditCardRepository.cardToCard(info);
                } else if (isAccountActive) {
                    System.out.println("invalid password!!!");
                }

            } else if (command.equals("2")) {
                System.out.println("enter your costumer id please: ");
                Long costumerId = longScanner.nextLong();
                List<Account> accounts = accountRepository.findAll(costumerId);
                if (accounts.size()>0) {
                    for (Account account : accounts) {
                        System.out.println(account.toString());
                    }
                }else {
                    System.out.println("costumer id not found !!!");
                }
            } else if (command.equals("3")) {

            } else if (command.equals("4")) {

            } else if (command.equals("5")) {

            } else if (command.equals("6")) {

            } else if (command.equals("7")) {
                System.out.println("enter admin password: ");
                String pass = scanner.nextLine();
                if (pass.equals("1111")) {
                    // TODO: 2/6/20
                }
            } else {
                System.out.println("wrong command!!!");
            }

        }
    }
}
