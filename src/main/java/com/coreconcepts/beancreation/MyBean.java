package com.coreconcepts.beancreation;

public class MyBean {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "message='"+message+'\''+'}';
    }
}
