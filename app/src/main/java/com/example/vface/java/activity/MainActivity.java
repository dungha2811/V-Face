package com.example.vface.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vface.R;

public class MainActivity extends AppCompatActivity {

    //initialize value
    ImageView icon;
    Button Login;
    Button Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set image for ImageView
        icon = findViewById(R.id.iv_icon);
        icon.setImageResource(R.drawable.icon);

        //handle the button
        Login = findViewById(R.id.btn_login);
        Register = findViewById(R.id.btn_register);

        //set onClick for each button
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //set on click for register button
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
