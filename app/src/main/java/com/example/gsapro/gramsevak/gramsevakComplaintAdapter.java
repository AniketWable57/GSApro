package com.example.gsapro.gramsevak;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsapro.Complaint;
import com.example.gsapro.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class gramsevakComplaintAdapter extends RecyclerView.Adapter<gramsevakComplaintAdapter.ViewHolder> {

    private List<Complaint> complaints;
    private FirebaseFirestore db;
    private Context context; // Add context

    public gramsevakComplaintAdapter(Context context, List<Complaint> complaints, FirebaseFirestore db) {
        this.context = context; // Initialize context
        this.complaints = complaints;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gramsevak_item_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint complaint = complaints.get(position);
        holder.complaintTextView.setText(complaint.getComplaint());
        holder.descriptionTextView.setText(complaint.getDescription());
        holder.statusTextView.setText(complaint.getStatus());
        holder.userEmailTextView.setText("Added by: " + complaint.getUserEmail());
        holder.idTextView.setText("ID: " + complaint.getId()); // Show the complaint ID

        // Change colors based on status
        updateStatusColors(holder, complaint.getStatus());

        holder.statusButton.setOnClickListener(view -> {
            String[] statusOptions = {"Pending", "Under Process", "Solved", "Rejected"};
            new AlertDialog.Builder(context)
                    .setTitle("Change Status")
                    .setItems(statusOptions, (dialog, which) -> {
                        String selectedStatus = statusOptions[which];
                        complaint.setStatus(selectedStatus);
                        Log.d("ComplaintAdapter", "Document ID: " + complaint.getId());

                        // Update Firestore with the new status
                        db.collection("complaints").document(complaint.getId())
                                .update("status", selectedStatus)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(context, "Status updated to: " + selectedStatus, Toast.LENGTH_SHORT).show();
                                    notifyItemChanged(position); // Refresh the item to reflect changes
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("ComplaintAdapter", "Error updating status", e);
                                    Toast.makeText(context, "Failed to update status", Toast.LENGTH_SHORT).show();
                                });
                    }).show();
        });
    }

    private void updateStatusColors(ViewHolder holder, String status) {
        // Change colors based on status
        int color;
        switch (status) {
            case "Pending":
                color = context.getResources().getColor(R.color.blue_700);
                break;
            case "Under Process":
                color = context.getResources().getColor(R.color.orange); // Ensure this color is defined in colors.xml
                break;
            case "Solved":
                color = context.getResources().getColor(R.color.success_green);
                break;
            case "Rejected":
                color = context.getResources().getColor(R.color.error_red);
                break;
            default:
                color = context.getResources().getColor(R.color.gray);
                break;
        }
        holder.statusTextView.setTextColor(color);
        holder.statusButton.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView complaintTextView, descriptionTextView, statusTextView, userEmailTextView, idTextView;
        Button statusButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintTextView = itemView.findViewById(R.id.complaintTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            userEmailTextView = itemView.findViewById(R.id.userEmailTextView);
            idTextView = itemView.findViewById(R.id.idTextView); // Ensure this ID is in your layout
            statusButton = itemView.findViewById(R.id.statusButton);
        }
    }
}
