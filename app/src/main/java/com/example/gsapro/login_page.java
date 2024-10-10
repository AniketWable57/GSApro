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

import com.example.gsapro.admin.admin_main;
import com.example.gsapro.gramsevak.gramsevak_main;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class login_page extends AppCompatActivity {
    private Button BtnRegister;
    private Spinner rolesp;
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;

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

        BtnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(login_page.this, registration_page_user.class);
            startActivity(intent);
        });

        ArrayList<String> roles = new ArrayList<>();
        roles.add("users");
        roles.add("GramSevak");
        roles.add("Admin");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        rolesp.setAdapter(adapter);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        String email = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String role = rolesp.getSelectedItem().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setEnabled(false); // Disable the button to prevent multiple clicks

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    btnLogin.setEnabled(true); // Re-enable the button

                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                            navigateToMainActivity(role);
                        }
                    } else {
                        // Login failed
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToMainActivity(String role) {
        Intent intent;
        switch (role) {
            case "users":
                intent = new Intent(this, user_main.class);
                break;
            case "GramSevak":
                intent = new Intent(this, gramsevak_main.class);
                break;
            case "Admin":
                intent = new Intent(this, admin_main.class);
                break;
            default:
                return;
        }
        startActivity(intent);
        finish(); // Close the login activity
    }
}
