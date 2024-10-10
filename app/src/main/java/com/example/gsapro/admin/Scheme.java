package com.example.gsapro.admin;

public class Scheme {
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

    public String getNeededDocuments() {
        return neededDocuments;
    }

    public String getSchemeCriteria() {
        return schemeCriteria;
    }

    public String getSchemeName() {
        return schemeName;
    }
}
