package com.shervinzadsoroor.models;

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private Branch branch; // many to one
    private Manager manager; // many to one
}
