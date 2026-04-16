package com.coreconcepts.autowired.byname;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context= new ClassPathXmlApplicationContext("autowiredbyname.xml");
        Car mycar=(Car)context.getBean("myCar");
        mycar.displayDetails();
    }
}
