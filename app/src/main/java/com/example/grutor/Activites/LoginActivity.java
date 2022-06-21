package com.example.grutor.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.grutor.R;

public class LoginActivity extends AppCompatActivity {

    public Button btnSignUp;
    public TextView tvSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hides action bar
        getSupportActionBar().hide();
        btnSignUp = findViewById(R.id.btnRegister);
        tvSignIn = findViewById(R.id.tvSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                 startActivity(i);
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });

        // Intent i = new Intent(this, FeedActivity.class);
        // startActivity(i);
    }
}