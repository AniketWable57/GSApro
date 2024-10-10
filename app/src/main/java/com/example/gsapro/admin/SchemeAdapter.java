package com.example.gsapro.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsapro.R;

import java.util.List;

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

    public SchemeAdapter(List<Scheme> schemeList) {
        this.schemeList = schemeList;
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

        holder.applyButton.setOnClickListener(v -> {
            // Handle apply button click
            Toast.makeText(v.getContext(), "Applied for " + scheme.getSchemeName(), Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public int getItemCount() {
        return schemeList.size();
    }
}

