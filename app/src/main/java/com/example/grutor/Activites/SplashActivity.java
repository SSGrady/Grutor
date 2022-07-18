package com.example.grutor.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class SplashActivity extends AppCompatActivity {
    ParseUser currentUser = ParseUser.getCurrentUser();
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity
                if (currentUser == null) {
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    i = new Intent(SplashActivity.this, FeedActivity.class);
                }
                startActivity(i);
                // close this activity
                finish();
            }
        }, 1500);
    }
}
