package com.example.gsapro.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsapro.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ManageSchemeAdapter extends RecyclerView.Adapter<ManageSchemeAdapter.SchemeViewHolder> {

    private List<Scheme> schemeList;
    private Context context;

    public ManageSchemeAdapter(List<Scheme> schemeList, Context context) {
        this.schemeList = schemeList;
        this.context = context;
    }

    @NonNull
    @Override
    public SchemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_scheme, parent, false);
        return new SchemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemeViewHolder holder, int position) {
        Scheme scheme = schemeList.get(position);
        holder.schemeNameTextView.setText("Name: " + scheme.getSchemeName());
        holder.schemeCriteriaTextView.setText("Criteria: " + scheme.getSchemeCriteria());
        holder.neededDocumentsTextView.setText("Documents: " + scheme.getNeededDocuments());

        // Edit button click listener
        holder.btnEditScheme.setOnClickListener(v -> {
            Intent intent = new Intent(context, add_new_schemes.class);
            intent.putExtra("schemeId", scheme.getId()); // Pass Scheme ID for editing
            intent.putExtra("schemeName", scheme.getSchemeName());
            intent.putExtra("schemeCriteria", scheme.getSchemeCriteria());
            intent.putExtra("neededDocuments", scheme.getNeededDocuments());
            context.startActivity(intent);
        });

        // Delete button click listener
        holder.btnDeleteScheme.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Scheme")
                    .setMessage("Are you sure you want to delete this scheme?")
                    .setPositiveButton("Yes", (dialog, which) -> deleteScheme(scheme.getId()))
                    .setNegativeButton("No", null)
                    .setCancelable(true)
                    .show();
        });
    }

    private void deleteScheme(String schemeId) {
        FirebaseFirestore.getInstance().collection("schemes").document(schemeId)
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Scheme deleted successfully.", Toast.LENGTH_SHORT).show();
                        if (context instanceof ManageSchemeActivity) {
                            ((ManageSchemeActivity) context).loadSchemeData();
                        }
                    } else {
                        Toast.makeText(context, "Failed to delete scheme.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return schemeList.size();
    }

    public static class SchemeViewHolder extends RecyclerView.ViewHolder {
        TextView schemeNameTextView, schemeCriteriaTextView, neededDocumentsTextView;
        Button btnEditScheme, btnDeleteScheme;

        public SchemeViewHolder(@NonNull View itemView) {
            super(itemView);
            schemeNameTextView = itemView.findViewById(R.id.textSchemeName);
            schemeCriteriaTextView = itemView.findViewById(R.id.textSchemeCriteria);
            neededDocumentsTextView = itemView.findViewById(R.id.textNeededDocuments);
            btnEditScheme = itemView.findViewById(R.id.btnEditScheme);
            btnDeleteScheme = itemView.findViewById(R.id.btnDeleteScheme);
        }
    }
}
