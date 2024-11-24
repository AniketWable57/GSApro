package com.example.gsapro.gramsevak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.gsapro.R;
import com.example.gsapro.login_page;
import com.example.gsapro.user_main;
import com.google.firebase.auth.FirebaseAuth;

public class gramsevak_main extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gramsevak_main2);

        // Initialize CardViews
        CardView complaintsCardView = findViewById(R.id.gramsevak_complaints);
        CardView logout = findViewById(R.id.user_logout);
        CardView solvedCardView = findViewById(R.id.complaints_solved);
        CardView pendingCardView = findViewById(R.id.complaints_pending);
        CardView underProcessCardView = findViewById(R.id.complaints_under_process);
        CardView rejectedCardView = findViewById(R.id.complaints_rejected);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                // After sign out, redirect to LoginActivity
                Intent intent = new Intent(gramsevak_main.this, login_page.class);
                startActivity(intent);
                finish();

            }
        });


        // Set click listeners
        complaintsCardView.setOnClickListener(v -> {
            Intent intent = new Intent(gramsevak_main.this, gramsevak_complaint_status.class);
            intent.putExtra("status", "All"); // Send identifier for all complaints
            startActivity(intent);
        });

        solvedCardView.setOnClickListener(v -> {
            Intent intent = new Intent(gramsevak_main.this, gramsevak_complaint_status.class);
            intent.putExtra("status", "Solved"); // Send identifier for solved complaints
            startActivity(intent);
        });

        pendingCardView.setOnClickListener(v -> {
            Intent intent = new Intent(gramsevak_main.this, gramsevak_complaint_status.class);
            intent.putExtra("status", "Pending"); // Send identifier for pending complaints
            startActivity(intent);
        });

        underProcessCardView.setOnClickListener(v -> {
            Intent intent = new Intent(gramsevak_main.this, gramsevak_complaint_status.class);
            intent.putExtra("status", "Under Process"); // Send identifier for under process complaints
            startActivity(intent);
        });

        rejectedCardView.setOnClickListener(v -> {
            Intent intent = new Intent(gramsevak_main.this, gramsevak_complaint_status.class);
            intent.putExtra("status", "Rejected"); // Send identifier for rejected complaints
            startActivity(intent);
        });
    }
}
