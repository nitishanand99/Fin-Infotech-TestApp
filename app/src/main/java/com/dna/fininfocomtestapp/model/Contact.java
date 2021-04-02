package com.dna.fininfocomtestapp.model;

public class Contact {
    private final String email;
    private final String number;

    public Contact(String email, String number) {
        this.email = email;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

}
