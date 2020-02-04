package com.shervinzadsoroor.models;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private Long id;
    private String firstName;
    private String lastName;
    private Branch branch; // one to one
    private List<Employee> employees = new ArrayList<>(); // one to many

}
