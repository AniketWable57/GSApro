// applied_schemes.java
package com.example.gsapro;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class applied_schemes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApplicationsAdapter adapter;
    private List<Application> applicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_schemes);

        recyclerView = findViewById(R.id.recyclerViewApplications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        applicationList = new ArrayList<>();
        adapter = new ApplicationsAdapter(applicationList, this);
        recyclerView.setAdapter(adapter);

        loadApplications();
    }

    private void loadApplications() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        CollectionReference applicationsRef = db.collection("users").document(userId).collection("applications");

        applicationsRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String schemeName = document.getString("schemeName");
                            Timestamp applicationDate = document.getTimestamp("applicationDate");
                            String status = document.getString("status");
                            String documentId = document.getId(); // Get document ID

                            // Add the application to the list with the document ID
                            applicationList.add(new Application(schemeName, applicationDate, status, documentId));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("Firestore", "No applications found for this user.");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error retrieving applications", e);
                });
    }
}
