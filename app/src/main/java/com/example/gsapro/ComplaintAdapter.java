package com.example.gsapro;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {

    private final List<Complaint> complaintList;

    public ComplaintAdapter(List<Complaint> complaintList) {
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);
        holder.textViewComplaint.setText(complaint.getComplaint());
        holder.textViewDescription.setText(complaint.getDescription());
        holder.textViewStatus.setText(complaint.getStatus());
        holder.textViewUserEmail.setText("Added by: " + complaint.getUserEmail());

        holder.deleteButton.setOnClickListener(v -> {
            // Implement delete functionality here
        });
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewComplaint, textViewDescription, textViewStatus, textViewUserEmail;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewComplaint = itemView.findViewById(R.id.textViewComplaint);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewUserEmail = itemView.findViewById(R.id.textViewUserEmail);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
