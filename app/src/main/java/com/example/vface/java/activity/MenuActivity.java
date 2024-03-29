package com.example.vface.java.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vface.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MenuActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button upload;
    private Button start;
    private Button signOut;
    private StorageReference storageRef;
    private FirebaseAuth firebaseAuth;

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

        getImageFromStorage();

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
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("list",list);
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //set on click for button sign out
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MenuActivity.this,
                        "Log Out Successfully!", Toast.LENGTH_SHORT).show();
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

        final StorageReference reference =
                storageRef.child("images/users/"+ userID+"/"+userID+".jpg");
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

    @Override
    protected void onResume() {
        getImageFromStorage();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        getImageFromStorage();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MenuActivity.this,
                "Log Out because app crashed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
