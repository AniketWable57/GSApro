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

public class GramsevakAdapter extends RecyclerView.Adapter<GramsevakAdapter.GramsevakViewHolder> {

    private List<Gramsevak> gramsevakList;
    private Context context;

    public GramsevakAdapter(List<Gramsevak> gramsevakList, Context context) {
        this.gramsevakList = gramsevakList;
        this.context = context;
    }

    @NonNull
    @Override
    public GramsevakViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gramsevak, parent, false);
        return new GramsevakViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GramsevakViewHolder holder, int position) {
        Gramsevak gramsevak = gramsevakList.get(position);
        holder.nameTextView.setText("Name: "+gramsevak.getName());
        holder.emailTextView.setText("Email: "+gramsevak.getEmail());
        holder.phoneTextView.setText("Mob.: "+gramsevak.getPhone());
        holder.aadharNumberTextView.setText("Adhar: "+gramsevak.getAadharNumber()); // New binding
        holder.assignedVillageTextView.setText("Village: "+gramsevak.getAssignedVillage()); // New binding
        holder.passwordTextView.setText("Password: "+gramsevak.getPassword()); // New binding

        // Edit button click listener
        holder.btnEditGramsevak.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddGramsevakActivity.class);
            intent.putExtra("gramsevakId", gramsevak.getId()); // Pass the Gramsevak ID for editing
            intent.putExtra("gramsevakName", gramsevak.getName());
            intent.putExtra("gramsevakEmail", gramsevak.getEmail());
            intent.putExtra("gramsevakPhone", gramsevak.getPhone());
            intent.putExtra("gramsevakAadharNumber", gramsevak.getAadharNumber()); // Pass Aadhar Number
            intent.putExtra("gramsevakAssignedVillage", gramsevak.getAssignedVillage()); // Pass Assigned Village
            intent.putExtra("gramsevakPassword", gramsevak.getPassword()); // Pass Password
            context.startActivity(intent);
        });

        // Delete button click listener
        holder.btnDeleteGramsevak.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Gramsevak")
                    .setMessage("Are you sure you want to delete this Gramsevak?")
                    .setPositiveButton("Yes", (dialog, which) -> deleteGramsevak(gramsevak.getId()))
                    .setNegativeButton("No", null)
                    .setCancelable(true) // Allow canceling the dialog
                    .show();
        });
    }

    private void deleteGramsevak(String gramsevakId) {
        FirebaseFirestore.getInstance().collection("gramsevak").document(gramsevakId)
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Gramsevak deleted successfully.", Toast.LENGTH_SHORT).show();
                        // Refresh the list after deletion
                        if (context instanceof ManageGramsevakActivity) {
                            ((ManageGramsevakActivity) context).loadGramsevakData();
                        }
                    } else {
                        Toast.makeText(context, "Failed to delete Gramsevak.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return gramsevakList.size();
    }

    public static class GramsevakViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, phoneTextView, aadharNumberTextView, assignedVillageTextView, passwordTextView;
        Button btnEditGramsevak, btnDeleteGramsevak;

        public GramsevakViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textName);
            emailTextView = itemView.findViewById(R.id.textEmail);
            phoneTextView = itemView.findViewById(R.id.textPhone);
            aadharNumberTextView = itemView.findViewById(R.id.textAadharNumber);
            assignedVillageTextView = itemView.findViewById(R.id.textAssignedVillage);
            passwordTextView = itemView.findViewById(R.id.textPassword);
            btnEditGramsevak = itemView.findViewById(R.id.btnEditGramsevak);
            btnDeleteGramsevak = itemView.findViewById(R.id.btnDeleteGramsevak);
        }
    }
}
