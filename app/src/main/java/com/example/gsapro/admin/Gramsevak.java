package com.example.gsapro.admin;

public class Gramsevak {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String aadharNumber; // New field
    private String assignedVillage; // New field
    private String password; // New field

    // Constructor
    public Gramsevak(){}
    public Gramsevak( String name, String email, String phone, String aadharNumber, String assignedVillage, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.aadharNumber = aadharNumber;
        this.assignedVillage = assignedVillage;
        this.password = password;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public String getAssignedVillage() {
        return assignedVillage;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public void setAssignedVillage(String assignedVillage) {
        this.assignedVillage = assignedVillage;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
