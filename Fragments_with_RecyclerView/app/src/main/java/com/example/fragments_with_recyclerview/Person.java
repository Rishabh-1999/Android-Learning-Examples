package com.example.fragments_with_recyclerview;

public class Person {
    private String name;
    private String telNr;

    public Person(String name, String tel) {
        this.name = name;
        this.telNr = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return telNr;
    }

    public void setTel(String tel) {
        this.telNr = tel;
    }
}
