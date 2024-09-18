package com.example.gsapro.admin;

import android.view.View;

public class gramsevakListModel {

    gramsevakListModel(View view){

    }

    public gramsevakListModel(String email, String name, String mobile, String password, String aadhaarNumber) {
        this.email = email;
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    String email,mobile,name,password,aadhaarNumber;
}
