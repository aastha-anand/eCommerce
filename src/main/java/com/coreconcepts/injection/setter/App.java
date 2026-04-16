package com.coreconcepts.injection.setter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context= new ClassPathXmlApplicationContext("applicationinjectionsetter.xml");
        Car mycar=(Car)context.getBean("myCar");
        mycar.displayDetails();
    }
}
