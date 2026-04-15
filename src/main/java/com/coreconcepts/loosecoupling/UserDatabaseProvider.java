package com.coreconcepts.loosecoupling;

public class UserDatabaseProvider implements UserDataProvider{
    @Override
    public String getUserDetails(){
        return "User Details from Database";
    }
}
