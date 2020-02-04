package com.shervinzadsoroor;

import com.shervinzadsoroor.models.Branch;
import com.shervinzadsoroor.repositories.HibernateInit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        HibernateInit.init();

    }
}
