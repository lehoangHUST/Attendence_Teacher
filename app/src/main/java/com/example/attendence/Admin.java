package com.example.attendence;

public class Admin {
    private String username;
    private String password;


    public  Admin(){

    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getAdminUser() {
        return username;
    }

    public String getAdminPass() {
        return password;
    }
}
