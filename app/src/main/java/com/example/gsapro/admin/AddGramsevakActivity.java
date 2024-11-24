package com.example.gsapro.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gsapro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddGramsevakActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhone, editTextAadharNumber, editTextAssignedVillage, editTextPassword;
    private Button buttonAddGramsevak;
    private FirebaseFirestore firestore;
    private String gramsevakId; // To hold the ID of the Gramsevak being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gramsevak2);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAadharNumber = findViewById(R.id.editTextAadharNumber); // New field
        editTextAssignedVillage = findViewById(R.id.editTextAssignedVillage); // New field
        editTextPassword = findViewById(R.id.editTextPassword); // New field
        buttonAddGramsevak = findViewById(R.id.buttonAddGramsevak);

        firestore = FirebaseFirestore.getInstance();

        // Get the data from Intent if editing
        gramsevakId = getIntent().getStringExtra("gramsevakId");
        if (gramsevakId != null) {
            // Prefill the fields with existing data
            editTextName.setText(getIntent().getStringExtra("gramsevakName"));
            editTextEmail.setText(getIntent().getStringExtra("gramsevakEmail"));
            editTextPhone.setText(getIntent().getStringExtra("gramsevakPhone"));
            editTextAadharNumber.setText(getIntent().getStringExtra("gramsevakAadharNumber")); // Prefill Aadhar Number
            editTextAssignedVillage.setText(getIntent().getStringExtra("gramsevakAssignedVillage")); // Prefill Assigned Village
            editTextPassword.setText(getIntent().getStringExtra("gramsevakPassword")); // Prefill Password
            buttonAddGramsevak.setText("Update Gramsevak"); // Change button text
        }

        buttonAddGramsevak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gramsevakId != null) {
                    updateGramsevak(); // Call update method if editing
                } else {
                    addGramsevak(); // Call add method if adding
                }
            }
        });
    }

    private void addGramsevak() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String aadharNumber = editTextAadharNumber.getText().toString().trim(); // New field
        String assignedVillage = editTextAssignedVillage.getText().toString().trim(); // New field
        String password = editTextPassword.getText().toString().trim(); // New field

        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter name");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter email");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            editTextPhone.setError("Please enter phone number");
            return;
        }
        if (TextUtils.isEmpty(aadharNumber)) {
            editTextAadharNumber.setError("Please enter Aadhar Number");
            return;
        }
        if (TextUtils.isEmpty(assignedVillage)) {
            editTextAssignedVillage.setError("Please enter Assigned Village");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter password");
            return;
        }

        Gramsevak gramsevak = new Gramsevak(name, email, phone, aadharNumber, assignedVillage, password); // Updated constructor

        firestore.collection("gramsevak")
                .add(gramsevak)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddGramsevakActivity.this, "Gramsevak added successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity after adding
                        } else {
                            Toast.makeText(AddGramsevakActivity.this, "Failed to add Gramsevak", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateGramsevak() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String aadharNumber = editTextAadharNumber.getText().toString().trim(); // New field
        String assignedVillage = editTextAssignedVillage.getText().toString().trim(); // New field
        String password = editTextPassword.getText().toString().trim(); // New field

        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter name");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter email");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            editTextPhone.setError("Please enter phone number");
            return;
        }
        if (TextUtils.isEmpty(aadharNumber)) {
            editTextAadharNumber.setError("Please enter Aadhar Number");
            return;
        }
        if (TextUtils.isEmpty(assignedVillage)) {
            editTextAssignedVillage.setError("Please enter Assigned Village");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter password");
            return;
        }

        Gramsevak updatedGramsevak = new Gramsevak(name, email, phone, aadharNumber, assignedVillage, password); // Updated constructor

        // Update the Firestore document
        firestore.collection("gramsevak").document(gramsevakId)
                .set(updatedGramsevak)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddGramsevakActivity.this, "Gramsevak updated successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity after updating
                        } else {
                            Toast.makeText(AddGramsevakActivity.this, "Failed to update Gramsevak", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
