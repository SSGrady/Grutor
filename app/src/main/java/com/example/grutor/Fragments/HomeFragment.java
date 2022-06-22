package com.example.grutor.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Activites.LoginActivity;
import com.example.grutor.R;
import com.parse.Parse;
import com.parse.ParseUser;

import java.util.Objects;


public class HomeFragment extends Fragment {

    public TextView tvWelcomeUser;
    public Button btnlogOut;
    public ParseUser currentUser;
    public static final String KEY_PARSE_USER_NAME = "name";
    protected ParseUser user;
    public static String welcomeMessage = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        tvWelcomeUser = view.findViewById(R.id.tvWelcomeUser);
        btnlogOut = view.findViewById(R.id.btnlogOut);

        user = ParseUser.getCurrentUser();
        // displays user's name on the welcome message
        welcomeMessage = String.format("Hey %s!", user.getUsername());
       tvWelcomeUser.setText(welcomeMessage);
        btnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                currentUser = ParseUser.getCurrentUser(); // now null
                goLoginActivity();
            }
        });

    }
    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}