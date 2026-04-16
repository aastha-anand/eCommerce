package com.coreconcepts.autowired.bytype;

public class Car {
   private Specfication specfication;

    public void setSpecfication(Specfication specfication) {
        this.specfication = specfication;
    }

    public void displayDetails(){
       System.out.println("Car Details: "+specfication.toString());
   }
}
