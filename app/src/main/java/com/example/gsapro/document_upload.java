package com.example.gsapro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class document_upload extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_CODE = 1;
    private String documentType;
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private LinearLayout documentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_document_upload);
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        documentContainer = findViewById(R.id.documentContainer);

        setupButton(R.id.uploadAadharButton, "Aadhaar Card");
        setupButton(R.id.uploadBankPassbookButton, "Bank Passbook");
        setupButton(R.id.uploadJobCardButton, "Job Card");
        setupButton(R.id.uploadRationCardButton, "Ration Card");

        loadUploadedDocuments();
    }
    private void setupButton(int buttonId, String type) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            documentType = type;
            pickFile();
        });
    }

    private void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // or "image/*" for images only
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            if (fileUri != null) {
                uploadDocument(fileUri);
            }
        }
    }

    private void uploadDocument(Uri fileUri) {
        String userId = "exampleUserId"; // Replace with actual user ID
        StorageReference storageRef = storage.getReference().child("documents/" + userId + "/" + documentType);

        storageRef.putFile(fileUri).addOnSuccessListener(taskSnapshot ->
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String url = uri.toString();
                    saveDocumentUrl(userId, documentType, url);
                    displayDocument(documentType, url);
                })
        );
    }

    private void saveDocumentUrl(String userId, String type, String url) {
        Map<String, Object> docData = new HashMap<>();
        docData.put(type, url);

        db.collection("users").document(userId)
                .set(docData, SetOptions.merge());
    }

    private void loadUploadedDocuments() {
        String userId = "exampleUserId"; // Replace with actual user ID
        db.collection("users").document(userId).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                            String type = entry.getKey();
                            String url = (String) entry.getValue();
                            displayDocument(type, url);
                        }
                    }
                });
    }

    private void displayDocument(String type, String url) {
        TextView textView = new TextView(this);
        textView.setText(type + ": " + url);
        documentContainer.addView(textView);
    }
}