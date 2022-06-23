package com.example.grutor.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Activites.LoginActivity;
import com.example.grutor.Adapters.SubjectAdapter;
import com.example.grutor.R;
import com.parse.Parse;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {

    public TextView tvWelcomeUser;
    public Button btnlogOut;
    public ParseUser currentUser;
    public static final String KEY_PARSE_USER_NAME = "name";
    protected ParseUser user;
    public static String welcomeMessage = "";
    RecyclerView rvSubjects;
    List<String> titles;
    List<Integer> images;

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
        // Recycler View population.
        rvSubjects = view.findViewById(R.id.rvSubjects);
        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("First Subject");
        titles.add("Second Subject");
        titles.add("Third Subject");
        titles.add("Fourth Subject");

        images.add(R.drawable.icons8_chat_bubble_50);
        images.add(R.drawable.icons8_chat_bubble_51);
        images.add(R.drawable.user_filled_24);
        images.add(R.drawable.user_outline_24);

        SubjectAdapter adapter = new SubjectAdapter(getContext(), titles, images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvSubjects.setLayoutManager(gridLayoutManager);
        rvSubjects.setAdapter(adapter);

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