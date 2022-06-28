package com.example.grutor.Fragments;

import static com.parse.Parse.getApplicationContext;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grutor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LessonsFragment extends Fragment {
    protected Bundle bundle;
    protected FloatingActionButton fab_main, fabCamera, fabMessages;
    protected Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView tvCallIcon, tvMessagesIcon;

    Boolean isOpen = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        bundle = this.getArguments();
        return inflater.inflate(R.layout.fragment_lessons, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        fab_main = view.findViewById(R.id.fab);
        fabCamera = view.findViewById(R.id.fabCamera);
        fabMessages = view.findViewById(R.id.fabMessages);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        tvCallIcon = view.findViewById(R.id.tvCallIcon);
        tvMessagesIcon = view.findViewById(R.id.tvMessagesIcon);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    tvCallIcon.setVisibility(View.INVISIBLE);
                    tvMessagesIcon.setVisibility(View.INVISIBLE);
                    fabMessages.startAnimation(fab_close);
                    fabCamera.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
                    fabMessages.setClickable(false);
                    fabCamera.setClickable(false);
                    isOpen = false;
                } else {
                    tvCallIcon.setVisibility(View.VISIBLE);
                    tvMessagesIcon.setVisibility(View.VISIBLE);
                    fabMessages.startAnimation(fab_open);
                    fabCamera.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fabMessages.setClickable(true);
                    fabCamera.setClickable(true);
                    isOpen = true;
                }

            }
        });


        fabMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Messages", Toast.LENGTH_SHORT).show();

            }
        });

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Call", Toast.LENGTH_SHORT).show();

            }
        });

        // TODO: Populate the Lessons fragment with new data.

    }
}