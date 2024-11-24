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

public class ManageSchemeActivity extends AppCompatActivity {

    private RecyclerView schemeRecyclerView;
    private Button btnAddScheme;
    private ManageSchemeAdapter schemeAdapter;
    private List<Scheme> schemeList;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_scheme);

        schemeRecyclerView = findViewById(R.id.schemeRecyclerView);
        btnAddScheme = findViewById(R.id.btnAddScheme);

        // Setting up the RecyclerView
        schemeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        schemeList = new ArrayList<>();
        schemeAdapter = new ManageSchemeAdapter(schemeList, this);
        schemeRecyclerView.setAdapter(schemeAdapter);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Handle "Add Scheme" button click
        btnAddScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageSchemeActivity.this, add_new_schemes.class));
            }
        });
    }

    // Load Scheme data when the activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        loadSchemeData();
    }

    // Public method to load Scheme data
    public void loadSchemeData() {
        firestore.collection("schemes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            schemeList.clear(); // Clear the current list
                            for (DocumentSnapshot doc : task.getResult()) {
                                Scheme scheme = doc.toObject(Scheme.class);
                                if (scheme != null) {
                                    scheme.setId(doc.getId()); // Set the document ID
                                    schemeList.add(scheme); // Add the Scheme to the list
                                }
                            }
                            schemeAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
                        } else {
                            // Log error if there was a failure
                            Exception exception = task.getException();
                            if (exception != null) {
                                Log.e("ManageSchemeActivity", "Error loading Scheme data", exception);
                            }
                            Toast.makeText(ManageSchemeActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
