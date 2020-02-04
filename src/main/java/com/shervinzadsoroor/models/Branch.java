package com.shervinzadsoroor.models;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private Long id;
    private String name;
    private List<Account> accounts = new ArrayList<>(); // one to many
    private List<Employee> employees = new ArrayList<>(); //one to many
    private Manager manager; // one to one
}
