package com.example.firebaserecyclerviewcrud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Random;

public class Adpter extends FirebaseRecyclerAdapter<Model,Adpter.Viewholder> {

    Context context;

    public Adpter(@NonNull FirebaseRecyclerOptions<Model> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Viewholder holder,  final  int position, @NonNull Model model) {


        holder.usename.setText(model.getUsername());
        holder.useemail.setText(model.getUseremail());
        Glide.with(holder.userdp).load(model.getUserdp()).into(holder.userdp);
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab=new AlertDialog.Builder(holder.usename.getContext());
                ab.setTitle("Are you sure?");
                ab.setMessage("Deleted data can't be undo?");
                ab.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        FirebaseDatabase.getInstance().getReference().child("User").child(getRef(position).getKey()).removeValue();

                    }
                });
                ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                ab.show();
            }
        });
        holder.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),updateActivity.class);
                intent.putExtra("name",model.getUsername());
                intent.putExtra("mail",model.getUseremail());
                intent.putExtra("image",model.getUserdp());
                intent.putExtra("id",model.getUserid());
                context.startActivity(intent);




            }
        });


    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.source,parent,false);
        return new Viewholder(view);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView userdp;
        TextView usename,useemail;
        Button deletebtn,updatebtn;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            updatebtn=itemView.findViewById(R.id.editbtn);
            userdp=itemView.findViewById(R.id.user_dp_card);
            useemail=itemView.findViewById(R.id.user_email_id);
            usename=itemView.findViewById(R.id.user_name_card);
            deletebtn=itemView.findViewById(R.id.deletebtn);

        }
    }
}
