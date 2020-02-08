package com.shervinzadsoroor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shervinzadsoroor.models.Account;
import com.shervinzadsoroor.models.Transaction;
import com.shervinzadsoroor.repositories.HibernateInit;
import com.shervinzadsoroor.repositories.impls.*;
import com.shervinzadsoroor.repositories.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws JsonProcessingException {
        HibernateInit hibernateInit = new HibernateInit();
        hibernateInit.init();
        Scanner scanner = new Scanner(System.in);
        Scanner longScanner = new Scanner(System.in);
        String command = null;

        CreditCardRepository creditCardRepository = new CreditCardRepositoryImpl();

        AccountRepository accountRepository = new AccountRepositoryImpl();

        EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();

        TransactionRepository transactionRepository = new TransactionRepositoryImpl();

        Validations validation = new ValidationImpl();

        while (true) {
            hibernateInit.showServices();
            System.out.println("choose your service please : ");
            command = scanner.nextLine();

            if (command.equals("1")) {
                String info = creditCardRepository.cardToCardInfo();

                boolean isInfoValid = validation.isCardToCardInfoValid(info);
                boolean isAccountActive = false;
                if (isInfoValid) {
                    isAccountActive = validation.isAccountActive(info);
                }
                boolean isPasswordValid = false;

                if (isInfoValid && isAccountActive) {
                    System.out.println("info is valid, please enter your password: ");
                    int password = Integer.parseInt(scanner.nextLine());
                    isPasswordValid = validation.isPasswordValid(password, info);
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
                String id = "{\"costumerId\":" + costumerId + "}";

                List<Account> accounts = accountRepository.findAll(id);
                if (accounts.size() > 0) {
                    for (Account account : accounts) {
                        System.out.println(account.toString());
                    }
                } else {
                    System.out.println("costumer id not found !!!");
                }
            } else if (command.equals("3")) {
                String info = accountRepository.getCreateAccountInfo();
                Long accountId = accountRepository.create(info);
                System.out.println("your account id is: " + accountId);

            } else if (command.equals("4")) {
                // TODO: 2/8/20  
            } else if (command.equals("5")) {
                // TODO: 2/8/20  
            } else if (command.equals("6")) {
                // TODO: 2/8/20
            } else if (command.equals("7")) {
                System.out.println("enter your account id: ");
                Long accountId = longScanner.nextLong();
                System.out.println("enter your password: ");
                int password = Integer.parseInt(scanner.nextLine());
                String jsonIdAndPass = "{\"id\":" + accountId + ",\"pass\":" + password + "}";
                boolean isIdOrPassValid = creditCardRepository.isPasswordValid(jsonIdAndPass);

                if (isIdOrPassValid) {
                    hibernateInit.showTransactionServices();
                    System.out.println("choose your service please : ");
                    command = scanner.nextLine();
                    List<Transaction> transactions = new ArrayList<>();
                    if (command.equals("1")) {
                        transactions = transactionRepository.findByAccountId(accountId);
                        transactionRepository.printTransactions(transactions);
                    } else if (command.equals("2")) {
                        System.out.println("enter date: (yyyy-mm-dd)");
                        String date = scanner.nextLine();
//                        String jsonSearchTransaction = "{\"id\":" + accountId + ",\"date\":" + date + "}";
                        transactions = transactionRepository.findByDateAndAccountId(accountId, date);
                        transactionRepository.printTransactions(transactions);
                    } else {
                        System.out.println("wrong command !!!");
                    }
                } else {
                    System.out.println("wrong account id or password !!!");
                }

            } else if (command.equals("8")) {

                System.out.println("enter your account id: ");
                Long accountId = longScanner.nextLong();
                System.out.println("enter your password: ");
                int password = Integer.parseInt(scanner.nextLine());
                String jsonIdAndPass = "{\"id\":" + accountId + ",\"pass\":" + password + "}";
                boolean isIdOrPassValid = creditCardRepository.isPasswordValid(jsonIdAndPass);

                if (isIdOrPassValid) {
                    hibernateInit.showPasswordServices();
                    System.out.println("choose your service please : ");
                    command = scanner.nextLine();
                    if (command.equals("1")) {

                        System.out.println("enter new first pass");
                        int newFirstPass1 = Integer.parseInt(scanner.nextLine());
                        System.out.println("enter the new pass again:");
                        int newFirstPass2 = Integer.parseInt(scanner.nextLine());
                        String jsonNewFirstPass = "{\"id\":" + accountId + ",\"pass\":" + newFirstPass1 + "}";

                        if (newFirstPass1 == newFirstPass2) {
                            creditCardRepository.changeFirstPass(jsonNewFirstPass);
                        } else {
                            System.out.println("wrong new password !!!");
                        }
                    } else if (command.equals("2")) {
                        String idStr = "{\"id\":" + accountId + "}";
                        creditCardRepository.assignSecondPass(idStr);

                    } else if (command.equals("3")) {
                        System.out.println("enter new second password: ");
                        int pass1 = longScanner.nextInt();
                        System.out.println("enter new second password again: ");
                        int pass2 = longScanner.nextInt();
                        if (pass1 == pass2) {
                            String jsonSecondPass = "{\"id\":" + accountId + ",\"pass\":" + pass1 + "}";
                            creditCardRepository.editSecondPass(jsonSecondPass);
                        } else {
                            System.out.println("wrong password !!!");
                        }
                    } else {
                        System.out.println("wrong command !!!");
                    }
                } else {
                    System.out.println("wrong account id or password !!!");
                }

            } else if (command.equals("9")) {
                System.out.println("enter admin password: ");
                String pass = scanner.nextLine();
                if (pass.equals("1111")) {
                    hibernateInit.showAdminServices();
                    System.out.println("choose your service please : ");
                    command = scanner.nextLine();
                    if (command.equals("1")) {
                        List<Account> deactiveAccounts = hibernateInit.showDeactiveAccounts();
                        System.out.println("enter account id to activate: ");
                        Long accountId = longScanner.nextLong();
                        employeeRepository.activateTheAccount(accountId, deactiveAccounts);

                    } else if (command.equals("2")) {
                        System.out.println("enter account id to delete: ");
                        Long accountId = longScanner.nextLong();
                        System.out.println("are you sure for deleting? ( Y | N )");
                        command = scanner.nextLine();
                        if (command.equalsIgnoreCase("Y")) {
                            employeeRepository.deleteAccount(accountId);
                        }
                    } else {
                        System.out.println("wrong command !!!");
                    }
                }
            } else {
                System.out.println("wrong command!!!");
            }

        }
    }
}
