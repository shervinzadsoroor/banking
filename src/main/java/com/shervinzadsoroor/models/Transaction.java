package com.shervinzadsoroor.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long depositAmount;

    @Column
    private Long withdrawAmount;

    @Column
    private String date;

    @Column
    private String description;

    @Column
    private boolean isSuccessful;

    @ManyToOne
    private Account account; // many to one

    public Transaction() {
    }
     public Transaction(Long depositAmount, Long withdrawAmount,String date, String description
     ,boolean isSuccessful , Account account){
        this.depositAmount = depositAmount;
        this.withdrawAmount = withdrawAmount;
        this.date = date;
        this.description = description;
        this.isSuccessful = isSuccessful;
        this.account = account;
     }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Long depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Long getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Long withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", depositAmount=" + depositAmount +
                ", withdrawAmount=" + withdrawAmount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", isSuccessful=" + isSuccessful +
                '}';
    }
}
