package com.example.gsapro;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComplaints;
    private ComplaintAdapter adapter;
    private List<Complaint> complaintList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        TextView textViewComplaints = findViewById(R.id.textViewComplaints);
        ImageView imageViewAddComplaint = findViewById(R.id.imageViewAddComplaint);
        recyclerViewComplaints = findViewById(R.id.recyclerViewComplaints);

        db = FirebaseFirestore.getInstance();
        complaintList = new ArrayList<>();
        adapter = new ComplaintAdapter(complaintList);
        recyclerViewComplaints.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComplaints.setAdapter(adapter);

        imageViewAddComplaint.setOnClickListener(v -> showComplaintDialog());

        loadComplaints();
    }

    private void showComplaintDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complaint, null);
        builder.setView(dialogView);

        EditText editTextComplaint = dialogView.findViewById(R.id.editTextComplaint);
        EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        EditText editTextStatus = dialogView.findViewById(R.id.editTextStatus);
        Button submitButton = dialogView.findViewById(R.id.submitButton);

        AlertDialog dialog = builder.create();

        submitButton.setOnClickListener(view -> {
            String complaint = editTextComplaint.getText().toString();
            String description = editTextDescription.getText().toString();
            String status = editTextStatus.getText().toString();

            if (!complaint.isEmpty() && !description.isEmpty() && !status.isEmpty()) {
                addComplaintToDatabase(complaint, description, status);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void addComplaintToDatabase(String complaint, String description, String status) {
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Map<String, Object> complaintData = new HashMap<>();
        complaintData.put("complaint", complaint);
        complaintData.put("description", description);
        complaintData.put("status", status);
        complaintData.put("userEmail", userEmail);

        db.collection("complaints")
                .add(complaintData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Complaint added", Toast.LENGTH_SHORT).show();
                    loadComplaints(); // Refresh the complaints list
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error adding complaint", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadComplaints() {
        db.collection("complaints")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        complaintList.clear();
                        QuerySnapshot documents = task.getResult();
                        for (DocumentSnapshot doc : documents.getDocuments()) {
                            Complaint complaint = doc.toObject(Complaint.class);
                            complaintList.add(complaint);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error loading complaints", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
