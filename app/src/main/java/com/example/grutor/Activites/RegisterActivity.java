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
    public EditText etFirstName;
    public EditText etLastName;
    public String userFullName;

    public Button btnNext;
    final Fragment fragmentRegister = new RegisterFragment();
    public static final String TAG = "RegisterActivity";
    public static ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // TODO: Add Parse Database and allow registration

        etUserLogin = findViewById(R.id.etUserLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnNext = findViewById(R.id.btnNext);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);


        final FragmentManager fragmentManager = getSupportFragmentManager();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Entering the registerUser function!");
                registerUser();
            }

            public void registerUser() {
                // Create the ParseUser
                user = new ParseUser();
                // Set core properties
                Log.i(TAG, "Inside the registerUser function.");
                String email = etUserLogin.getText().toString();
                String password = etPassword.getText().toString();
                String firstName = etFirstName.getText().toString();

                // this is the full name of the user who will be registered to the Parse database.
                user.put("name", etFirstName.getText().toString() + " " + etLastName.getText().toString());
                user.setEmail(email);
                user.setPassword(password);

                // username is a required field -- Grutor has not use for one but this field can be used
                user.setUsername(firstName); // field stores user's first name for the welcome message
                fragmentManager.beginTransaction().replace(R.id.flContainerSignUp, fragmentRegister).commit();
            }
        });
    }
}