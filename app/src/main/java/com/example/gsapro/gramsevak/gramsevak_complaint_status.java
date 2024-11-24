package com.example.gsapro.gramsevak;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsapro.Complaint;
import com.example.gsapro.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class gramsevak_complaint_status extends AppCompatActivity {

    private RecyclerView complaintsRecyclerView;
    private gramsevakComplaintAdapter complaintAdapter;
    private FirebaseFirestore db;
    private String statusFilter; // Status filter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gramsevak_complaint_status);

        // Get the status from the intent
        statusFilter = getIntent().getStringExtra("status");

        complaintsRecyclerView = findViewById(R.id.complaintsRecyclerView);
        complaintsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        loadComplaints();
    }

    private void loadComplaints() {
        // Create a query based on the status filter
        if ("All".equals(statusFilter)) {
            db.collection("complaints").get()
                    .addOnSuccessListener(querySnapshot -> handleQueryResult(querySnapshot))
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed to load complaints", Toast.LENGTH_SHORT).show());
        } else {
            db.collection("complaints").whereEqualTo("status", statusFilter).get()
                    .addOnSuccessListener(querySnapshot -> handleQueryResult(querySnapshot))
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed to load complaints", Toast.LENGTH_SHORT).show());
        }
    }

    private void handleQueryResult(QuerySnapshot querySnapshot) {
        List<Complaint> complaints = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot) {
            Complaint complaint = document.toObject(Complaint.class);
            complaint.setId(document.getId()); // Ensure the ID is set
            complaints.add(complaint);
        }

        if (complaints.isEmpty()) {
            Toast.makeText(this, "No complaints found", Toast.LENGTH_SHORT).show();
        }

        complaintAdapter = new gramsevakComplaintAdapter(this, complaints, db);
        complaintsRecyclerView.setAdapter(complaintAdapter);
    }
}
