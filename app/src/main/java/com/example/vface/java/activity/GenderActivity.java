package com.example.vface.java.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.vface.R;
import com.example.vface.java.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenderActivity extends AppCompatActivity {

    private ImageView male;
    private ImageView female;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        //define variable
        male = (ImageView) findViewById(R.id.iv_male);
        female = (ImageView) findViewById(R.id.iv_female);

        male.setImageResource(R.drawable.male);
        female.setImageResource(R.drawable.female);

        //set onclick for male choice
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenderActivity.this,
                        FriendsActivity.class);
                Bundle bundle = new Bundle();

                //add data into bundle
                bundle.putString("gender","Male");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        //set onclick for female choice
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenderActivity.this,
                        FriendsActivity.class);
                Bundle bundle = new Bundle();

                //add data into bundle
                bundle.putString("gender","Female");
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
