package com.shervinzadsoroor.models;

import java.util.ArrayList;
import java.util.List;

public class Costumer {
    private Long id;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private List<Account> accounts = new ArrayList<>();  // one to many
}
