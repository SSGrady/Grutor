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
    protected ParseUser currentUser;
    protected ParseUser user;
    protected String welcomeMessage = "";
    public RecyclerView rvSubjects;
    private List<String> titles;
    private List<Integer> images;
    protected SubjectAdapter adapter;
    protected  GridLayoutManager gridLayoutManager;

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

        createCard();

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

    private void createCard() {
        titles.add(getString(R.string.math));
        titles.add(getString(R.string.english));
        titles.add(getString(R.string.science));
        titles.add(getString(R.string.history));
        titles.add(getString(R.string.government));
        titles.add(getString(R.string.economics));

        images.add(R.drawable.icons8_math_64);
        images.add(R.drawable.icons8_english_64);
        images.add(R.drawable.icons8_physics_64);
        images.add(R.drawable.icons8_history_64);
        images.add(R.drawable.icons8_government_64);
        images.add(R.drawable.icons8_stock_share_64);

        adapter = new SubjectAdapter(getContext(), titles, images);
        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}