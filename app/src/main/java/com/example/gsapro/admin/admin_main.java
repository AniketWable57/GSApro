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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.gsapro.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class admin_main extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton btn_drawer_menu;
    NavigationView navigationView;
    TextView adminUsername, adminMail;







    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main);

        drawerLayout = findViewById(R.id.drawer);
        btn_drawer_menu  = findViewById(R.id.btnDrawerTongle);
        navigationView = findViewById(R.id.navigationView);
        adminUsername = findViewById(R.id.adminusername);
        adminMail = findViewById(R.id.adminemail);



        btn_drawer_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }



        });

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                    Toast.makeText(admin_main.this, "Add scheme Clicked", Toast.LENGTH_SHORT).show();
                }

                if (itemId == R.id.gramsevakList){
                    Intent toGramsevakList = new Intent(admin_main.this, gramsevak_list.class);
                    startActivity(toGramsevakList);

                }

                drawerLayout.close();

                return false;
            }
        });



    }
}