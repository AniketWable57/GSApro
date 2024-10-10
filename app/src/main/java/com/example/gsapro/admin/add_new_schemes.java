package com.example.gsapro.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gsapro.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class add_new_schemes extends AppCompatActivity {

    private EditText etSchemeName, etSchemeCriteria, etNeededDocuments;
    private Button btnAddScheme;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_schemes);

        db = FirebaseFirestore.getInstance();

        etSchemeName = findViewById(R.id.etSchemeName);
        etSchemeCriteria = findViewById(R.id.etSchemeCriteria);
        etNeededDocuments = findViewById(R.id.etNeededDocuments);
        btnAddScheme = findViewById(R.id.btnAddScheme);

        btnAddScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addScheme();
            }
        });
    }

    private void addScheme() {
        String schemeName = etSchemeName.getText().toString().trim();
        String schemeCriteria = etSchemeCriteria.getText().toString().trim();
        String neededDocuments = etNeededDocuments.getText().toString().trim();

        if (schemeName.isEmpty() || schemeCriteria.isEmpty() || neededDocuments.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new scheme object
        Map<String, Object> scheme = new HashMap<>();
        scheme.put("schemeName", schemeName);
        scheme.put("schemeCriteria", schemeCriteria);
        scheme.put("neededDocuments", neededDocuments);

        // Add a new document with a generated ID
        db.collection("schemes")
                .add(scheme)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Scheme added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error adding scheme: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        etSchemeName.setText("");
        etSchemeCriteria.setText("");
        etNeededDocuments.setText("");
    }
}