package com.example.grutor.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.grutor.Activites.LoginActivity;
import com.example.grutor.R;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {

    public Button btnlogOut;
    protected ParseUser currentUser;
    protected TextView tvProfileName;
    protected TextView tvUserBestSubject;
    protected TextView tvUserCurrentGrade;
    protected TextView tvUserHoursStudied;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        currentUser = ParseUser.getCurrentUser();
        btnlogOut = view.findViewById(R.id.btnlogOut);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvUserBestSubject = view.findViewById(R.id.tvBestSubjectAnswer);
        tvUserCurrentGrade = view.findViewById(R.id.tvCurrentGradeAnswer);
        tvUserHoursStudied = view.findViewById(R.id.tvTotalHoursAnswer);

        if (currentUser != null)  {
        tvProfileName.setText(currentUser.get("name").toString());
        tvUserHoursStudied.setText(currentUser.get("hoursCount").toString());
        tvUserBestSubject.setText(currentUser.get("bestAt").toString());
        tvUserCurrentGrade.setText(currentUser.get("grade").toString());
        }
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