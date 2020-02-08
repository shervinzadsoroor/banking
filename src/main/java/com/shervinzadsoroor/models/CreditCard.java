package com.shervinzadsoroor.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
public class CreditCard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String cardNumber;

    @Column
    private int firstPass;

    @Column
    private String secondPass;

    @Column
    private boolean isActive;

    @Column
    private int CVV2;

    @Column
    private String expireDate;

    @OneToOne(mappedBy = "creditCard")
    private Account account; // one to one

    public CreditCard() {
    }

    public CreditCard(String cardNumber, int CVV2, String expireDate) {
        this.cardNumber = cardNumber;
        firstPass = 1111;
        isActive = true;
        this.CVV2 = CVV2;
        this.expireDate = expireDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getFirstPass() {
        return firstPass;
    }

    public void setFirstPass(int firstPass) {
        this.firstPass = firstPass;
    }

    public String getSecondPass() {
        return secondPass;
    }

    public void setSecondPass(String secondPass) {
        this.secondPass = secondPass;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCVV2() {
        return CVV2;
    }

    public void setCVV2(int CVV2) {
        this.CVV2 = CVV2;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", firstPass=" + firstPass +
                ", secondPass=" + secondPass +
                ", isActive=" + isActive +
                ", CVV2=" + CVV2 +
                ", expireDate=" + expireDate +
                '}';
    }
}
