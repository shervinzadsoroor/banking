package com.shervinzadsoroor.models;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private Long id;
    private Long balance;
    private boolean isActive;
    private Costumer costumer; //many to one
    private Branch branch; // many to one
    private CreditCard creditCard; // one to one
    private List<Transaction> transactions = new ArrayList<>(); // one to many
}
