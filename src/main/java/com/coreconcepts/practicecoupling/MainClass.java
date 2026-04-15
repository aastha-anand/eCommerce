package com.coreconcepts.practicecoupling;

public class MainClass {
    public static void main(String[] args) {
        DataProvider dataProvider =new UserDatabase();
        DataManager dataManager=new DataManager(dataProvider);
        System.out.println(dataManager.getInfo());

        DataProvider dataProvider2 =new UserWebdata();
        DataManager dataManager2=new DataManager(dataProvider2);
        System.out.println(dataManager2.getInfo());
     }
}
