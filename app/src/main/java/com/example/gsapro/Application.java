// Application.java
package com.example.gsapro;

import com.google.firebase.Timestamp;

public class Application {
    private String schemeName;
    private Timestamp applicationDate;
    private String status;
    private String documentId;  // New field for Firestore document ID

    public Application(String schemeName, Timestamp applicationDate, String status, String documentId) {
        this.schemeName = schemeName;
        this.applicationDate = applicationDate;
        this.status = status;
        this.documentId = documentId;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public Timestamp getApplicationDate() {
        return applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public String getDocumentId() {
        return documentId;
    }
}
