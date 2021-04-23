package com.epam.training.ticketservice.domain;

public class User {
    private String name;
    private String password;
    private Boolean isAdmin;


    public User(String name, String password, Boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }
}
