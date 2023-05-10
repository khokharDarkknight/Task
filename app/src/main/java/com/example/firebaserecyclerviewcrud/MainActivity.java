package com.example.firebaserecyclerviewcrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    Adpter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerid);

        actionButton=findViewById(R.id.floatingbtn);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("User");
        FirebaseRecyclerOptions<Model> options=new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(databaseReference,Model.class)
                .build();
        adpter=new Adpter(options,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adpter);


    }

    @Override
    protected void onStart() {
        adpter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        adpter.stopListening();
        super.onStop();
    }
}