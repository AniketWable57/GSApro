package com.example.gsapro;

import com.example.gsapro.admin.view_schemes;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;  // Optional for loading images
import com.example.gsapro.admin.admin_main;
import com.example.gsapro.admin.view_schemes;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user_main extends AppCompatActivity {

    CardView user_home,user_scheme_list,user_profile,user_settings,user_applied_schemes,user_logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        user_home = findViewById(R.id.user_home);
        user_scheme_list = findViewById(R.id.user_scheme_list);
        user_logout = findViewById(R.id.user_logout);


        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Get the current logged-in user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Retrieve user information
            String userId = currentUser.getUid();
            String currentUserEmail = currentUser.getEmail();
            String userDisplayName = currentUser.getDisplayName(); // Optional, may be null
            String userPhone = currentUser.getPhoneNumber(); // If phone authentication was used



        } else {
            // No user is signed in
            Log.d("FirebaseUser", "No user is currently signed in.");
        }

        user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();

            }
        });



        user_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(user_main.this, "Home Cliscked!!", Toast.LENGTH_SHORT).show();
            }
        });

        user_scheme_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toViewSchemes = new Intent(user_main.this, view_schemes.class);
                startActivity(toViewSchemes);
            }
        });



        
    }

    private void logoutUser() {
        // Log out the user from Firebase
        mAuth.signOut();

        // After sign out, redirect to LoginActivity
        Intent intent = new Intent(user_main.this, login_page.class);

        // Clear the activity stack to prevent the user from going back to the main activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Optionally finish the current activity
        finish();
    }


}
