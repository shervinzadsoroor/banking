package com.shervinzadsoroor.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Branch implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<Costumer> costumers = new ArrayList<>(); // one to many

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<Employee> employees = new ArrayList<>(); //one to many

    @OneToOne(cascade = CascadeType.ALL)
    private Manager manager; // one to one


    public Branch() {
    }

    public Branch(Manager manager, List<Employee> employees, List<Costumer> costumers) {
        this.manager = manager;
        this.employees = employees;
        this.costumers = costumers;
    }

    public Branch(Manager manager) {
        this.manager = manager;
    }

    public Branch(Manager manager, List<Employee> employees) {
        this.manager = manager;
        this.employees = employees;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Costumer> getCostumers() {
        return costumers;
    }

    public void setCostumers(List<Costumer> costumers) {
        this.costumers = costumers;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
