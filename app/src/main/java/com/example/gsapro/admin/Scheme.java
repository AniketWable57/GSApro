package com.example.gsapro.admin;

public class Scheme {
    private String id; // Add an id field to store the document ID
    private String neededDocuments;
    private String schemeCriteria;
    private String schemeName;

    public Scheme() {
        // Default constructor required for calls to DataSnapshot.getValue(Scheme.class)
    }

    public Scheme(String neededDocuments, String schemeCriteria, String schemeName) {
        this.neededDocuments = neededDocuments;
        this.schemeCriteria = schemeCriteria;
        this.schemeName = schemeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNeededDocuments() {
        return neededDocuments;
    }

    public void setNeededDocuments(String neededDocuments) {
        this.neededDocuments = neededDocuments;
    }

    public String getSchemeCriteria() {
        return schemeCriteria;
    }

    public void setSchemeCriteria(String schemeCriteria) {
        this.schemeCriteria = schemeCriteria;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }
}
