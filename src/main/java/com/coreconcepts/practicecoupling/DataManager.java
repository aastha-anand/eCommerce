package com.coreconcepts.practicecoupling;

public class DataManager {
    private DataProvider dataProvider;

    public DataManager(DataProvider dataProvider){
        this.dataProvider=dataProvider;
    }
    public String getInfo(){
        return dataProvider.getDetails();
    }
}
