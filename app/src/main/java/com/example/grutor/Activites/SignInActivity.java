package com.example.grutor.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grutor.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class SignInActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    protected EditText etUserLogin;
    protected EditText etPasswordLogin;
    public Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        // hides action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_sign_in);

        etUserLogin = findViewById(R.id.etUserLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUserLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();
                loginUser(username, password);
            }
        });
    }


        private void loginUser(String username, String password) {
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e!= null) {
                        Log.e(TAG, "Issue with login", e);
                        Toasty.error(SignInActivity.this, "Login failure!", Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    if (ParseUser.getCurrentUser().get("isNewUser").equals(true)) {
                        Toasty.success(SignInActivity.this, "Welcome " + ParseUser.getCurrentUser().getUsername() + "!", Toast.LENGTH_SHORT, true).show();
                    }
                    else {
                        Toasty.success(SignInActivity.this, "Welcome back " + ParseUser.getCurrentUser().getUsername() + "!", Toast.LENGTH_SHORT, true).show();
                    }
                    goMainActivity();
                    Toasty.Config.getInstance().apply(); // required
                }
            });

        }

        private void goMainActivity() {
            Intent i = new Intent(this, FeedActivity.class);
            startActivity(i);
            finish();
        }
}