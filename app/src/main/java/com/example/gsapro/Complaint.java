package com.example.gsapro;

public class Complaint {
    private String id; // Document ID
    private String complaint; // Complaint text
    private String description; // Description of the complaint
    private String status; // Current status of the complaint
    private String userEmail; // Email of the user who added the complaint

    // Default constructor for Firestore
    public Complaint() { }

    // Setters
    public void setId(String id) { // Setter for ID
        this.id = id;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
