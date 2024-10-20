package com.example.gsapro.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.gsapro.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class admin_main extends AppCompatActivity {

    DrawerLayout admin_drawer;
    ImageButton butoon_drawer_toggle;
    NavigationView navigationView;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main);

        admin_drawer = findViewById(R.id.admin_main_drawer);
        butoon_drawer_toggle = findViewById(R.id.btnDrawerTongle);
        navigationView = findViewById(R.id.navigationView);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        butoon_drawer_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_drawer.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.viewProfile){
                    Toast.makeText(admin_main.this, "Vew Profile Clicked", Toast.LENGTH_SHORT).show();
                }

                if (itemId == R.id.addGramsevak){
                    Intent toAddGramSevakpage = new Intent(admin_main.this, add_gramsevak.class);
                    startActivity(toAddGramSevakpage);

                }

                if (itemId == R.id.addScheme){
                    Intent toAddNewScheme = new Intent(admin_main.this, add_new_schemes.class);
                    startActivity(toAddNewScheme);
                }

                if (itemId == R.id.gramsevakList){
                    Intent toGramsevakList = new Intent(admin_main.this, gramsevak_list.class);
                    startActivity(toGramsevakList);

                }
                if (itemId == R.id.SchemeList){
                    Intent toSchemeList = new Intent(admin_main.this, view_schemes.class);
                    startActivity(toSchemeList);

                }

                admin_drawer.close();

                return false;
            }
        });



    }
    public void updateNavHeader(){
        navigationView = findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.adminusername);
        TextView navEmail = headerView.findViewById(R.id.adminemail);

        navUsername.setText(currentUser.getEmail());
        navEmail.setText(currentUser.getDisplayName());
    }
}
