package com.example.grutor.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.grutor.Fragments.HomeFragment;
import com.example.grutor.Fragments.RegisterFragment;
import com.example.grutor.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {


    public  EditText etUserLogin;
    public  EditText etPassword;
    public Button btnNext;
    final Fragment fragmentRegister = new RegisterFragment();
    public static final String TAG = "RegisterActivity";
    protected ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // TODO: Add Parse Database and allow registration

        etUserLogin = findViewById(R.id.etUserLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnNext = findViewById(R.id.btnNext);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Entering the registerUser function!");
                registerUser();
            }

            private void registerUser() {
                // Create the ParseUser
                user = new ParseUser();
                // Set core properties
                Log.i(TAG, "Inside the registerUser function.");
                String username = etUserLogin.getText().toString();
                String password = etPassword.getText().toString();
                // TODO: Make email et field and findViewById it to send the email to Parse
                // user.setEmail(email);
                user.setPassword(password);
                user.setUsername(username);

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            // Hooray! Let them use the app now.
                            Log.e(TAG, "Failed to register a user.", e);
                        }
                        Log.i(TAG, "Succeeded to register a user!");
                        setupUser(username, password);
                    }
                });
            }

            private void setupUser(String username, String password) {
                if (ParseUser.getCurrentUser() != null) {
                    // Navigate to the Register fragment if the user has registered to parse properly
                    goRegisterUser(user, username, password);
                }
            }

            public void goRegisterUser(ParseUser user, String username, String password) {
                fragmentManager.beginTransaction().replace(R.id.flContainerSignUp, fragmentRegister).commit();
            }
        });
    }
}