package com.example.gsapro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gsapro.admin.admin_main;
import com.example.gsapro.gramsevak.gramsevak_main;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class login_page extends AppCompatActivity {
Button BtnRegister;
    Spinner rolesp;
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private FirebaseFirestore db;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
         rolesp = findViewById(R.id.spinnerRole);
        BtnRegister = findViewById(R.id.idBtnRegister);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);


        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_page.this, registration_page_user.class);
                startActivity(intent);
            }
        });

        rolesp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("users");
        arrayList.add("GramSevak");
        arrayList.add("Admin");
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        rolesp.setAdapter(adapter);

// Initialize Firestore
        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(view -> {
            // Capture input data
            String email = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String role = rolesp.getSelectedItem().toString().trim();

            // Check if fields are empty
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(login_page.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection(role)
                    .whereEqualTo("email",email )
                    .whereEqualTo("password", password)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                           QuerySnapshot querySnapshot = task.getResult();
                            if (!querySnapshot.isEmpty()) {
                                // Login successful, proceed to next activity
                                Toast.makeText(login_page.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                if (role == "users") {
                                    Intent intent = new Intent(login_page.this, user_main.class);
                                    startActivity(intent);
                                    finish(); // Optional: Close the login activity
                                } else if (role == "GramSevak") {
                                    Intent intent = new Intent(login_page.this, gramsevak_main.class);
                                    startActivity(intent);
                                    finish(); // Optional: Close the login activity
                                    
                                } else if (role == "Admin") {
                                    Intent intent = new Intent(login_page.this, admin_main.class);

                                    startActivity(intent);
                                     // Optional: Close the login activity
                                }
                            } else {
                                // Login failed, no user found
                                Toast.makeText(login_page.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle errors
                            Toast.makeText(login_page.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        


    }
}