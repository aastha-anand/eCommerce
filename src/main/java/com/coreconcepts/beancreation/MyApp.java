package com.coreconcepts.beancreation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyApp {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("ApplicationBeanContext.xml");
        MyBean myBean=(MyBean) context.getBean("MyBean");
        System.out.println(myBean);

    }
}
