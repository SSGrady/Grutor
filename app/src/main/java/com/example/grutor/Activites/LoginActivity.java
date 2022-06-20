package com.example.grutor.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.grutor.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hides action bar
        getSupportActionBar().hide();



        // Intent i = new Intent(this, FeedActivity.class);
        // startActivity(i);
    }
}