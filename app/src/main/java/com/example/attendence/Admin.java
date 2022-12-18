package com.example.attendence;

public class Admin {
    private String adminUser;
    private String adminPass;


    public  Admin(){

    }

    public Admin(String username, String password) {
        this.adminUser = username;
        this.adminPass = password;
    }

    public String getAdminUser() {
        return this.adminUser;
    }

    public String getAdminPass() {
        return this.adminPass;
    }
}
