package com.example.vface.java.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vface.R;
import com.example.vface.java.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button upload;
    private Button start;
    private Button signOut;
    private StorageReference storageRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    private List<User> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        //define variable
        upload = (Button) findViewById(R.id.btn_upload);
        start =(Button) findViewById(R.id.btn_start);
        signOut = (Button)findViewById(R.id.btn_logout);
        imageView = (ImageView) findViewById(R.id.iv_image);
        storageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_gender);

        getImageFromStorage();
        getUserList();

        //set on click for button upload
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,UploadActivity.class);
                startActivity(intent);
            }
        });

        //set on click for button start
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, GenderActivity.class);
                startActivity(intent);
            }
        });

        //set on click for button sign out
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MenuActivity.this,"Log Out Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Get the current user image
     */
    public void getImageFromStorage(){

        //get the signed in user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        String userID = user.getUid();

        final StorageReference reference = storageRef.child("images/users/"+ userID+"/"+userID+".jpg");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
                Log.d("Menu", String.valueOf(uri));


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Log.d("Menu", "fail");
                imageView.setImageResource(R.drawable.image_not_available);
            }
        });
    }

    public void getUserList() {
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                User user = document.toObject(User.class);
                                Log.d("Dung", user.getUsername());
                            }
                        } else {
                            Log.d("Dung", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
