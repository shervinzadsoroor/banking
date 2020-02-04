package com.shervinzadsoroor.models;

import java.util.Date;

public class Transaction {
    private Long id;
    private Long depositAmount;
    private Long withdrawAmount;
    private Date date;
    private String description;
    private boolean isSuccessful;
    private Account account; // many to one
}
