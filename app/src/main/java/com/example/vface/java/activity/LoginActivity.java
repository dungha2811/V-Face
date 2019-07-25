package com.example.vface.java.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vface.R;
import com.example.vface.java.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button Login;
    Button Register;

    private static final String TAG = "LoginActivity";

    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //handle editText
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        //handle button
        Login = findViewById(R.id.btn_login);
        Register = findViewById(R.id.btn_register);

        //set on click for register button
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        setUpFireBase();
        //set on click for sign in Button
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }
    private void setUpFireBase(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        //if user exist
                        if(user!=null){
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            FirebaseFirestoreSettings  settings = new FirebaseFirestoreSettings.Builder()
                                    .build();
                            db.setFirestoreSettings(settings);

                            //get user by their ID
                            DocumentReference userRef = db.collection(getString(R.string.collection_users))
                                    .document(user.getUid());
                            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    User user = Objects.requireNonNull(task.getResult()).toObject(User.class);
                                }

                            });

                        }
                    }
                };

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
    }
    private void signIn(){

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString()
                ,password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                task.isSuccessful();
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                Log.d("Dung","Success");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,"Email or password wrong or not matching", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
