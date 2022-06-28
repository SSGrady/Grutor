package com.example.grutor.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Activites.RegisterActivity;
import com.example.grutor.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterFragment extends Fragment {
    private Spinner spGrades;
    private Spinner spSubjects;
    private Boolean gradeSelect = false;
    private Boolean subjectSelect = false;
    public static final String TAG = "Registration Fragment";
    protected String grade;
    protected String bestAt;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        bundle = this.getArguments();
        return inflater.inflate(R.layout.fragment_register, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        spGrades = view.findViewById(R.id.spGrade);
        spSubjects = view.findViewById(R.id.spSubjects);

        ArrayAdapter<CharSequence>gradeAdapter= ArrayAdapter.createFromResource(getContext(), R.array.grades, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence>subjectAdapter= ArrayAdapter.createFromResource(getContext(), R.array.subjects, android.R.layout.simple_spinner_item);

        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spGrades.setAdapter(gradeAdapter);
        spSubjects.setAdapter(subjectAdapter);

        spGrades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spGrades.getSelectedItem().toString().equals("Grade")) {
                    gradeSelect = true;
                    isUserSet();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gradeSelect = false;
            }
        });

        spSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spSubjects.getSelectedItem().toString().equals("Subject")) {
                    subjectSelect = true;
                    isUserSet();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subjectSelect = false;
            }
        });
    }

    private void isUserSet() {
        if (gradeSelect.equals(subjectSelect)) {
            completeRegistration();
        }
    }

    private void completeRegistration() {
        ParseUser user = new ParseUser();
        grade = spGrades.getSelectedItem().toString();
        bestAt = spSubjects.getSelectedItem().toString();

        user.setUsername(bundle.getString("firstName"));
        user.put("name", bundle.getString("fullName"));
        user.setPassword(bundle.getString("password"));
        user.setEmail(bundle.getString("email"));
        user.put("grade", grade);
        user.put("bestAt", bestAt);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    // Hooray! Let them use the app now.
                    Log.e(TAG, "Failed to register a user.", e);
                    return;
                }
                Log.i(TAG, "Succeeded to register a user!");
                goToFeed();
            }
        });
    }

    private void goToFeed() {
        Intent i = new Intent(getContext(), FeedActivity.class);
        startActivity(i);
    }
}