package com.example.grutor.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    public Button btnNext;
    final Fragment fragmentRegister = new RegisterFragment();
    public static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etUserLogin = findViewById(R.id.etUserLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnNext = findViewById(R.id.btnNext);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);


        final FragmentManager fragmentManager = getSupportFragmentManager();

        // hides action bar
        getSupportActionBar().hide();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Entering the registerUser function!");
                registerUser();
            }

            public void registerUser() {
                // Set core properties
                String email = "";
                Log.i(TAG, "Inside the registerUser function.");

                // if the user's email is invalid
                if (!isValidEmail(etUserLogin.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Invalid Email. Try again!", Toast.LENGTH_SHORT).show();
                }
                // TODO: (stretch feature) Check the username length and password length with a counter
                else {
                    email = etUserLogin.getText().toString();
                    String password = etPassword.getText().toString();
                    String firstName = etFirstName.getText().toString();
                    String fullName = etFirstName.getText().toString() + " " + etLastName.getText().toString();

                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("password", password);
                    bundle.putString("firstName", firstName);
                    bundle.putString("fullName", fullName);
                    fragmentRegister.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.flContainerSignUp, fragmentRegister).commit();
                }
            }

            private boolean isValidEmail(String target) {
                return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
            }
        });
    }
}