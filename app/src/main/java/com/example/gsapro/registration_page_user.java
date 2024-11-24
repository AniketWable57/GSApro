package com.example.gsapro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.Firebase;

import java.util.HashMap;
import java.util.Map;

public class registration_page_user extends AppCompatActivity {

    //Declarations
    private EditText etName,etMobile,etAddress,etEmail,etPassword,etConfirmPassword,etAdhar;
    private Button btnRegister;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressBar progressBar;
    TextView already_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration_page_user);


        //Registrations
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        already_account = findViewById(R.id.txt_already_account);
        etAdhar = findViewById(R.id.etAdhar);
        //progressBar = findViewById(R.id.progressBar);

        //firebase instance
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent toLoginPage = new Intent(registration_page_user.this, login_page.class);
                  startActivity(toLoginPage);
                  finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString().trim();
                String mobile = etMobile.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String adhar = etAdhar.getText().toString().trim();


                if (name.isEmpty() || mobile.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(registration_page_user.this, "All Fields are complesary", Toast.LENGTH_SHORT).show();
                    // Handle empty fields, e.g., by showing a Toast message to the user
                } else if (!password.equals(confirmPassword)) {

                    Toast.makeText(registration_page_user.this, "Password Dosent Match", Toast.LENGTH_SHORT).show();
                }

               // progressBar.setVisibility();

                Map<String, Object> user = new HashMap<>();
                user.put("name", name);
                user.put("mobile", mobile);
                user.put("address", address);
                user.put("email", email);
                user.put("adhar", adhar);
                user.put("password", password);



                // Add a new document with a generated ID


                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            sendUserToNextActivity();
                            Toast.makeText(registration_page_user.this, "Authwnticated!!", Toast.LENGTH_SHORT).show();

                            String userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = db.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", name);
                            user.put("mobile", mobile);
                            user.put("address", address);
                            user.put("email", email);
                            user.put("password", password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(registration_page_user.this, "User Creates"+userID, Toast.LENGTH_SHORT).show();
                                }
                            });
                            


                           /* db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener(documentReference -> {
                                        // Show success message
                                        Toast.makeText(registration_page_user.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                                        // Reset the input fields
                                        etName.setText("");
                                        etMobile.setText("");
                                        etAddress.setText("");
                                        etEmail.setText("");
                                        etPassword.setText("");
                                        etConfirmPassword.setText("");

                                    })
                                    .addOnFailureListener(e -> Log.w("UserRegistration", "Error adding document", e));


                            */

                        }else{
                            Toast.makeText(registration_page_user.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });







                //taking data from UserModule
                //UserModel data = new UserModel(name,mobile,email,address,password);
                // db.collection("user").document().set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                //     @Override
                //     public void onComplete(@NonNull Task<Void> task) {
                //         if (task.isSuccessful()){
                //             Toast.makeText(registration_page_user.this, "User Registered Successfully!!", Toast.LENGTH_SHORT).show();
                //         }else{
                //             Toast.makeText(registration_page_user.this, "Error !", Toast.LENGTH_SHORT).show();
                //         }
                //
                //     }
                // });


            }
        });

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(registration_page_user.this,user_main.class);
        startActivity(intent);
    }
}