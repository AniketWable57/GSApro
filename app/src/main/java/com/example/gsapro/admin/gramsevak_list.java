package com.example.gsapro.admin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsapro.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.Firebase;
import com.google.firebase.database.FirebaseDatabase;

public class gramsevak_list extends AppCompatActivity {

    RecyclerView recyclerView;
    GramsevakListAdapter gramsevakListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gramsevak_list);


        recyclerView = (RecyclerView)findViewById(R.id.glrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<gramsevakListModel> options =
                new FirebaseRecyclerOptions.Builder<gramsevakListModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("GramSevak"),gramsevakListModel.class)
                        .build();
        gramsevakListAdapter = new GramsevakListAdapter(options);
        recyclerView.setAdapter(gramsevakListAdapter);


    }
}