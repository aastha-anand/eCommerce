package com.loose.coupling;

public class LooseCouplingExample {
    public static void main(String[] args) {
        UserManager userManager=new UserManager();
        System.out.println(userManager.getUserInfo());
    }
}
