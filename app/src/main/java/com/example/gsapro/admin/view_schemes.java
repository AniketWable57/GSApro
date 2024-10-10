package com.example.gsapro.admin;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsapro.R;import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class view_schemes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SchemeAdapter schemeAdapter;
    private List<Scheme> schemeList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schemes);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        schemeList = new ArrayList<>();
        schemeAdapter = new SchemeAdapter(schemeList);
        recyclerView.setAdapter(schemeAdapter);

        db = FirebaseFirestore.getInstance();
        fetchSchemesFromFirestore();
    }

    private void fetchSchemesFromFirestore() {
        db.collection("schemes") // Replace with your Firestore collection name
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Scheme scheme = document.toObject(Scheme.class);
                            schemeList.add(scheme);
                        }
                        schemeAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("FirestoreError", "Error getting documents: ", task.getException());
                    }
                });
    }
}