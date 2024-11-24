package com.example.gsapro.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.gsapro.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class admin_main extends AppCompatActivity {

    private DrawerLayout admin_drawer;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);

        admin_drawer = findViewById(R.id.admin_main_drawer);
        navigationView = findViewById(R.id.navigationView);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Set up the action bar toggle for the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, admin_drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        admin_drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set navigation item selection listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.admin_dashboard) {
                    Toast.makeText(admin_main.this, "Dashboard Clicked", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.nav_manage_gramsevak) {
                    startActivity(new Intent(admin_main.this, ManageGramsevakActivity.class));
                } else if (itemId == R.id.nav_manage_schemes) {
                    startActivity(new Intent(admin_main.this, ManageSchemeActivity.class));
                } else if (itemId == R.id.nav_logout) {
                    mAuth.signOut();
                    Toast.makeText(admin_main.this, "Logged out", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                }

                // Close the drawer after item selection
                admin_drawer.closeDrawers();
                return true;
            }

        });
    }


//    private void updateNavHeader() {
//        View headerView = navigationView.getHeaderView(0);
//        TextView navUsername = headerView.findViewById(R.id.adminusername);
//        TextView navEmail = headerView.findViewById(R.id.adminemail);
//
//        if (currentUser != null) {
//            navUsername.setText(currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "Admin");
//            navEmail.setText(currentUser.getEmail());
//        }
//    }
}
