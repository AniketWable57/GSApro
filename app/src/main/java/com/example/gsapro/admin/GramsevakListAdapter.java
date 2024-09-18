package com.example.gsapro.admin;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsapro.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class GramsevakListAdapter extends FirebaseRecyclerAdapter<gramsevakListModel,GramsevakListAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public GramsevakListAdapter(@NonNull FirebaseRecyclerOptions<gramsevakListModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull gramsevakListModel model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.mobile.setText(model.getMobile());
        holder.adharnumber.setText(model.getAadhaarNumber());
        holder.password.setText(model.getPassword());



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gramsevak_list_card,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,email,mobile,password,adharnumber;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.nameTextView);
            email = (TextView)itemView.findViewById(R.id.emailTextView);
            mobile = (TextView)itemView.findViewById(R.id.phoneTextView);
            password = (TextView)itemView.findViewById(R.id.passwordTextView);
            adharnumber = (TextView)itemView.findViewById(R.id.aadhaarTextView);
        }
    }
}
