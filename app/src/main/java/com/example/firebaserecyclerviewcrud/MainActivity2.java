package com.example.firebaserecyclerviewcrud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {


    ImageView userimage;
    Button addbtn;
    Uri uri;
    Bitmap bitmap;
    EditText useemail,usename;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        usename=findViewById(R.id.user_name_id);
        useemail=findViewById(R.id.user_email_id);
        userimage=findViewById(R.id.user_dp);
        addbtn=findViewById(R.id.addBTN);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("uploading...");
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent,1);

            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=usename.getText().toString();
                String mail=useemail.getText().toString();
             if (TextUtils.isEmpty(name) && TextUtils.isEmpty(mail))
             {
                 Toast.makeText(MainActivity2.this, "Enter Field", Toast.LENGTH_SHORT).show();
             }
             else
             {
                uploadtofirebase(name,mail);
                progressDialog.show();
            }}
        });
    }

    private void uploadtofirebase(String name, String mail) {
        StorageReference storageReference= FirebaseStorage.getInstance().getReference("image1"+new Random().nextInt(15));
        storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("User");
                        String uid=databaseReference.push().getKey();
                        Model model=new Model(name,mail,uid,uri.toString());
                        databaseReference.child(uid).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                useemail.setText(null);
                                usename.setText(null);
                                progressDialog.dismiss();
                            }
                        });



                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
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
        userimage.setImageURI(uri);
    }
}