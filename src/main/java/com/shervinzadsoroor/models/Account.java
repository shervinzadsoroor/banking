package com.shervinzadsoroor.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Account implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long balance;

    @Column
    private boolean isActive;

    @Column
    private int numOfWrongPassEntered;

    @ManyToOne
    private Costumer costumer; //many to one


    @OneToOne(cascade = CascadeType.ALL)
    private CreditCard creditCard; // one to one

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>(); // one to many

    public Account() {
    }

    public Account(Long balance, Costumer costumer, CreditCard creditCard) {
        this.balance = balance;
        isActive = true;
        numOfWrongPassEntered = 0;
        this.costumer = costumer;
        this.creditCard = creditCard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getNumOfWrongPassEntered() {
        return numOfWrongPassEntered;
    }

    public void setNumOfWrongPassEntered(int numOfWrongPassEntered) {
        this.numOfWrongPassEntered = numOfWrongPassEntered;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }


    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", isActive=" + isActive +
                ", numOfWrongPassEntered=" + numOfWrongPassEntered +
                '}';
    }
}
