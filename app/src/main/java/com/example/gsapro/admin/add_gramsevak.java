package com.example.gsapro.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gsapro.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class add_gramsevak extends AppCompatActivity {

    private EditText etName, etEmail, etMobile, etAadhaarNumber, etPassword;
    private Button btnAddGramsevak, btnbackToadmin;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_gramsevak);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etAadhaarNumber = findViewById(R.id.etAadhaarNumber);
        etPassword = findViewById(R.id.etPassword);
        btnAddGramsevak = findViewById(R.id.btnAddGramsevak);
        btnbackToadmin = findViewById(R.id.btnbackToadmin);





        btnbackToadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToAdmin = new Intent(add_gramsevak.this, admin_main.class);
                startActivity(backToAdmin);
                finish();
            }
        });


        // Set up button click listener
        btnAddGramsevak.setOnClickListener(view -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String mobile = etMobile.getText().toString().trim();
            String aadhaarNumber = etAadhaarNumber.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validate input fields
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(mobile)
                    || TextUtils.isEmpty(aadhaarNumber) || TextUtils.isEmpty(password)) {
                Toast.makeText(add_gramsevak.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add Gramsevak data to Firestore
            addGramsevakToFirestore(name, email, mobile, aadhaarNumber, password);
        });
    }

    private void addGramsevakToFirestore(String name, String email, String mobile, String aadhaarNumber, String password) {
        // Create a new Gramsevak object
        Map<String, Object> gramsevak = new HashMap<>();
        gramsevak.put("name", name);
        gramsevak.put("email", email);
        gramsevak.put("mobile", mobile);
        gramsevak.put("aadhaarNumber", aadhaarNumber);
        gramsevak.put("password", password);

        // Add data to Firestore
        db.collection(" GramSevak")
                .add(gramsevak)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(add_gramsevak.this, " GramSevak  added successfully", Toast.LENGTH_SHORT).show();
                    // Clear the fields after successful registration
                    clearFields();
                })
                .addOnFailureListener(e -> Toast.makeText(add_gramsevak.this, "Error adding Gramsevak: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Method to clear the fields after successful registration
    private void clearFields() {
        etName.setText("");
        etEmail.setText("");
        etMobile.setText("");
        etAadhaarNumber.setText("");
        etPassword.setText("");
    }





}