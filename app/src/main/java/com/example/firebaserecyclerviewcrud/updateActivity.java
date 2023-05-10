package com.example.firebaserecyclerviewcrud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class updateActivity extends AppCompatActivity {


    Uri uri;
    Button update,newimage;
    TextView email,name;
    ImageView imageView;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        email=findViewById(R.id.update_email_id);
        name=findViewById(R.id.update_name_id);
        update=findViewById(R.id.update_id);
        imageView=findViewById(R.id.update_dp);
        update=findViewById(R.id.update_id);
        newimage=findViewById(R.id.update_imagebtn);
        email.setText(getIntent().getStringExtra("mail"));
        name.setText(getIntent().getExtras().getString("name"));
        uid=getIntent().getStringExtra("id");
        Glide.with(imageView).load(getIntent().getStringExtra("image")).into(imageView);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email.getText().toString();
                String Name=name.getText().toString();

                if (Email.isEmpty()||Name.isEmpty())
                {
                    Toast.makeText(updateActivity.this, "Fill the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    StorageReference storageReference= FirebaseStorage.getInstance().getReference("image12"+new Random().nextInt(10));
                    storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Toast.makeText(updateActivity.this, "fill", Toast.LENGTH_SHORT).show();
                                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("User").child(uid);
                                    HashMap map=new HashMap();
                                    map.put("userdp",uri.toString());
                                    map.put("useremail",Email);
                                    map.put("username",Name);

                                    reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if (task.isComplete())
                                            {
                                                Toast.makeText(updateActivity.this, "success ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(updateActivity.this, "eerr"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println(e);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println(e);
                        }
                    });

                }


            }
        });

        newimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent,1);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==RESULT_OK)
        {
            uri=data.getData();
        }
        imageView.setImageURI(uri);

    }
}