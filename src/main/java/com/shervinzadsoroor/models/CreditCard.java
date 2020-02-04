package com.shervinzadsoroor.models;

import java.util.Date;

public class CreditCard {
    private Long id;
    private String cardNumber;
    private int firstPass;
    private int secondPass;
    private boolean isActive;
    private int CVV2;
    private Date expireDate;
    private Account account; // one to one
}
