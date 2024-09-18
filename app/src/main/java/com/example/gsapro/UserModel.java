package com.example.gsapro;

public class UserModel {
    String name,mobile_no,email,address,password,confirmPassword;

    public UserModel(String name, String mobile, String email, String address, String password) {

    }

    public UserModel(String name, String mobile_no, String email, String address, String password, String confirmPassword) {
        this.name = name;
        this.mobile_no = mobile_no;
        this.email = email;
        this.address = address;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
