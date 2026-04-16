package com.coreconcepts.injection.construtor;

public class Car {
   private Specfication specfication;
   public Car(Specfication specfication){
       this.specfication=specfication;
   }
   public void displayDetails(){
       System.out.println("Car Details: "+specfication.toString());
   }
}
