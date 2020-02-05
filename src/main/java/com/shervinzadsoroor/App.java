package com.shervinzadsoroor;

import com.shervinzadsoroor.repositories.HibernateInit;
import com.shervinzadsoroor.repositories.impls.CreditCardRepositoryImpl;
import com.shervinzadsoroor.repositories.impls.ValidationImpl;
import com.shervinzadsoroor.repositories.interfaces.CreditCardRepository;
import com.shervinzadsoroor.repositories.interfaces.Validations;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        HibernateInit hibernateInit = new HibernateInit();
        hibernateInit.init();
        Scanner scanner = new Scanner(System.in);
        String command = null;
        CreditCardRepository creditCardRepository =
                new CreditCardRepositoryImpl();

        Validations validation = new ValidationImpl();

        while (true) {
            hibernateInit.showServices();
            System.out.println("choose your service please : ");
            command = scanner.nextLine();

            if (command.equals("1")) {
                Long[] info = creditCardRepository.cardToCardInfo();
                boolean isInfoValid = validation.isCardToCardInfoValid(info);
                boolean isPasswordValid = false;
                if (isInfoValid) {
                    System.out.println("info is valid, please enter your password: ");
                    int password = Integer.parseInt(scanner.nextLine());
                    Long sourceCardNumber = info[0];
                    isPasswordValid = validation.isPasswordValid(password, sourceCardNumber);
                } else {
                    System.out.println("invalid cards info!!!");
                }
                if (isPasswordValid) {
                    creditCardRepository.cardToCard(info);
                } else {
                    System.out.println("invalid password!!!");
                }

            } else if (command.equals("2")) {

            } else if (command.equals("3")) {

            } else if (command.equals("4")) {

            } else if (command.equals("5")) {

            }

        }
    }
}
