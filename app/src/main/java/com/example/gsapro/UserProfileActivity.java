package com.example.gsapro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvMobile, tvAddress, tvAdhar;
    private Button btnUpdate, btnBack;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        // Initialize Firestore and FirebaseAuth
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvMobile = findViewById(R.id.tv_mobile);
        tvAddress = findViewById(R.id.tv_address);
        tvAdhar = findViewById(R.id.tv_adhar);

        //buttons intitializations
        btnBack = findViewById(R.id.btn_back);
        btnUpdate = findViewById(R.id.btn_update);

        // Get logged-in user's ID
        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                tvName.setText(documentSnapshot.getString("name"));
                tvEmail.setText(documentSnapshot.getString("email"));
                tvMobile.setText(documentSnapshot.getString("mobile"));
                tvAddress.setText(documentSnapshot.getString("address"));
                tvAdhar.setText(documentSnapshot.getString("adhar"));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toBack = new Intent(UserProfileActivity.this,user_main.class);
                startActivity(toBack);
                finish();
            }
        });



    }
}
