package com.example.balanceviewer.Class;

/* Made by Rishabh Anand */

public class Person {
    private String name;
    private String Balance;

    public Person(String name, String Balance) {
        this.name = name;
        this.Balance = Balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return Balance;
    }
}
