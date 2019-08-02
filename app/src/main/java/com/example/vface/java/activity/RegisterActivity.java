package com.example.vface.java.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vface.R;
import com.example.vface.java.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Calendar;
import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private EditText rePassword;
    private EditText phoneNumber;
    private EditText emailAddress;
    private TextView date;
    private RadioGroup radioGroup;
    private DatePickerDialog.OnDateSetListener mDate;
    private String datedb = "";
    private String gender;
    private ProgressDialog mProgressDialog;

    private FirebaseFirestore mDb ;

    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDb = FirebaseFirestore.getInstance();

        //define variable
        emailAddress = findViewById(R.id.et_email);
        userName = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        rePassword= findViewById(R.id.et_reEnterPassword);
        phoneNumber = findViewById(R.id.et_phoneNumber);
        date = findViewById(R.id.tv_pick_age);
        radioGroup = findViewById(R.id.rg_gender);
        mProgressDialog = new ProgressDialog(RegisterActivity.this);
        Button register = (findViewById(R.id.btn_register));


        //set on click to pick date
        date.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                @SuppressLint("ResourceType") DatePickerDialog dialog = new DatePickerDialog(
                        RegisterActivity.this,
                        2
                        , mDate,year,month,day);
               (Objects.requireNonNull(dialog.getWindow()))
                       .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month+=1;
                datedb = day + "-" + month + "-" + year;
                String dateShow = day + "/" + month + "/" + year;
                date.setText(dateShow);
            }
        };

        //set on click for register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgressDialog.setMessage("Register new user with email address"
                        + emailAddress.getText().toString() +" ...");
                mProgressDialog.show();

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioButton = findViewById(selectedId);

                gender = radioButton.getText().toString();
                DocumentReference userRef = mDb.collection("Users")
                        .document("emailAddress");
                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                Toast.makeText(RegisterActivity.this,
                                        "Email Address already exist",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                register(emailAddress.getText().toString(),
                                        phoneNumber.getText().toString(),
                                        password.getText().toString(),
                                        userName.getText().toString(),
                                        gender, datedb);
                            }
                        }
                    }
                });
                }
        });
    }

    /**
     * Register user to database
     */
    public void register(final String email, final String phoneNumber, final String password,
                         final String username, final String gender, final String date){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //generate Id
                        String Id = FirebaseAuth.getInstance().getUid();

                        //input data
                        User user = new User();
                        user.setPhoneNumber(phoneNumber);
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setFaceVector("To be imported");
                        user.setDateOfBirth(date);
                        user.setGender(gender);
                        user.setEmailAddress(email);
                        user.setImageLink("TBD");
                        user.setId(Id);
                        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                                .build();
                        mDb.setFirestoreSettings(settings);

                        if (!Id.isEmpty()) {
                            DocumentReference newUserRef = mDb
                                    .collection(getString(R.string.collection_users))
                                    .document(Objects.
                                            requireNonNull(Id));
                            newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this,
                                                "Successfully create user",
                                                Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this
                                            , LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegisterActivity.this
                                                , "Something went wrong",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }

                });
    }

    public boolean allowRegister(){
        if(!password.equals(rePassword)){
            Toast.makeText(RegisterActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
