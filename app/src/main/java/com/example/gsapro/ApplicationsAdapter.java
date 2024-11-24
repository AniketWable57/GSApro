// ApplicationsAdapter.java
package com.example.gsapro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ApplicationViewHolder> {

    private final List<Application> applicationList;
    private final Context context;

    public ApplicationsAdapter(List<Application> applicationList, Context context) {
        this.applicationList = applicationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_application, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        Application application = applicationList.get(position);
        holder.schemeNameTextView.setText(application.getSchemeName());
        holder.statusTextView.setText(application.getStatus());
        holder.applicationDateTextView.setText(application.getApplicationDate().toDate().toString());

        // Set up delete button listener
        holder.deleteButton.setOnClickListener(v -> deleteApplication(application, position));
    }

    private void deleteApplication(Application application, int position) {
        // Reference to the specific application document to delete
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(userId)
                .collection("applications").document(application.getDocumentId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Remove the item from the list and notify adapter
                    applicationList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Application deleted successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to delete application", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    static class ApplicationViewHolder extends RecyclerView.ViewHolder {
        TextView schemeNameTextView, statusTextView, applicationDateTextView;
        Button deleteButton;

        public ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            schemeNameTextView = itemView.findViewById(R.id.textViewSchemeName);
            statusTextView = itemView.findViewById(R.id.textViewStatus);
            applicationDateTextView = itemView.findViewById(R.id.textViewApplicationDate);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
