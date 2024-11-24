package com.example.gsapro.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsapro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ManageGramsevakActivity extends AppCompatActivity {

    private RecyclerView gramsevakRecyclerView;
    private Button btnAddGramsevak;
    private GramsevakAdapter gramsevakAdapter;
    private List<Gramsevak> gramsevakList;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_gramsevak);

        gramsevakRecyclerView = findViewById(R.id.gramsevakRecyclerView);
        btnAddGramsevak = findViewById(R.id.btnAddGramsevak);

        // Setting up the RecyclerView
        gramsevakRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gramsevakList = new ArrayList<>();
        gramsevakAdapter = new GramsevakAdapter(gramsevakList, this);
        gramsevakRecyclerView.setAdapter(gramsevakAdapter);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Handle "Add Gramsevak" button click
        btnAddGramsevak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageGramsevakActivity.this, AddGramsevakActivity.class));
            }
        });
    }

    // Load Gramsevak data when the activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        loadGramsevakData();
    }

    // Public method to load Gramsevak data
    public void loadGramsevakData() {
        firestore.collection("gramsevak")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            gramsevakList.clear(); // Clear the current list
                            for (DocumentSnapshot doc : task.getResult()) {
                                Gramsevak gramsevak = doc.toObject(Gramsevak.class);
                                if (gramsevak != null) {
                                    gramsevak.setId(doc.getId()); // Set the document ID
                                    gramsevakList.add(gramsevak); // Add the Gramsevak to the list
                                }
                            }
                            gramsevakAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
                        } else {
                            // Log error if there was a failure
                            Exception exception = task.getException();
                            if (exception != null) {
                                Log.e("ManageGramsevakActivity", "Error loading Gramsevak data", exception);
                            }
                            Toast.makeText(ManageGramsevakActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
