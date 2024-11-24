package com.example.gsapro.admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsapro.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.SchemeViewHolder> {
    private List<Scheme> schemeList;

    public static class SchemeViewHolder extends RecyclerView.ViewHolder {
        public TextView schemeName;
        public TextView schemeCriteria;
        public TextView neededDocuments;
        public Button applyButton;







        public SchemeViewHolder(View itemView) {
            super(itemView);
            schemeName = itemView.findViewById(R.id.scheme_name);
            schemeCriteria = itemView.findViewById(R.id.scheme_criteria);
            neededDocuments = itemView.findViewById(R.id.needed_documents);
            applyButton = itemView.findViewById(R.id.apply_button);
        }
    }
    private Context context;
    public SchemeAdapter(List<Scheme> schemeList, Context context) {
        this.schemeList = schemeList;
        this.context = context;
    }

    @NonNull
    @Override
    public SchemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scheme_item, parent, false);
        return new SchemeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemeViewHolder holder, int position) {

        Scheme scheme = schemeList.get(position);
        holder.schemeName.setText(scheme.getSchemeName());
        holder.schemeCriteria.setText(scheme.getSchemeCriteria());
        holder.neededDocuments.setText(scheme.getNeededDocuments());

        String schemeName = scheme.getSchemeName();

        holder.applyButton.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userId = auth.getCurrentUser().getUid();
            DocumentReference userDocRef = db.collection("users").document(userId);
/*
            //DocumentReference userRef = db.collection("users").document("userId"); // Replace "userId" with the actual user document ID

            userDocRef.update("applied_schemes", FieldValue.arrayUnion(schemeName)) // Replace "schemeName" with the actual scheme name
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Firestore", "Scheme added to applied_schemes array successfully!");
                    })
                    .addOnFailureListener(e -> {
                        Log.w("Firestore", "Error adding scheme to applied_schemes array", e);
                    });

 */


            Map<String, Object> applicationData = new HashMap<>();
            applicationData.put("schemeName", schemeName);
            applicationData.put("applicationDate", new Timestamp(new Date()));
            applicationData.put("status", "Pending");

            userDocRef.collection("applications")
                    .add(applicationData)  // This creates a new document with an auto-generated ID in "applications"
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(v.getContext(), "Applied for " + scheme.getSchemeName(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.w("Firestore", "Error adding application", e);
                    });




        });
    }


    @Override
    public int getItemCount() {
        return schemeList.size();
    }
}

