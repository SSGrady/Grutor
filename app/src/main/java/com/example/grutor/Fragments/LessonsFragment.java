package com.example.grutor.Fragments;

import static com.parse.Parse.getApplicationContext;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grutor.Adapters.LessonAdapter;
import com.example.grutor.Adapters.MatchesAdapter;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.example.grutor.Utility.studentMatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class LessonsFragment extends Fragment {
    protected FloatingActionButton fab_main, fabCamera, fabMessages;
    protected Animation fab_open, fab_close, fab_clock, fab_anticlock;
    protected studentMatcher matching;
    TextView tvCallIcon, tvMessagesIcon;
    RecyclerView rvLessons, rvMatches;
    LessonAdapter lessonsAdapter;
    MatchesAdapter matcher;
    ArrayList<Lessons> lessons;
    Button btnMatch;
    Boolean isOpen = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lessons, parent, false);
    }

    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        btnMatch = view.findViewById(R.id.btnMatch);
        rvLessons = view.findViewById(R.id.rvLessons);
        rvMatches = view.findViewById(R.id.rvMatches);
        lessons = new ArrayList<>();
        try {
            queryLessons();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lessonsAdapter = new LessonAdapter(getContext(), lessons);
        // TODO [ ] Add onClick listener for btnSubjectTopic that populates the matches adapter with the correct subject,
        // TODO     this will happen inside studentMatcher.java where queried results are based on btnSubjectTopic's subject

        rvLessons.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLessons.setAdapter(lessonsAdapter);
        btnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matching = new studentMatcher(lessonsAdapter.requestedLessonString);
                try {
                    matching.getMyMatches();
                    matcher = new MatchesAdapter(getContext(), matching.matches, lessonsAdapter.requestedLesson);
                    rvMatches.setAdapter(matcher);
                    rvMatches.setLayoutManager(new LinearLayoutManager(getContext()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

      setFloatingActionButton(view);
    }

    private void setFloatingActionButton(View view) {
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
    }

    private void queryLessons() throws ParseException {
        ParseQuery<Lessons> query = ParseQuery.getQuery(Lessons.class);
        query.setLimit(5);
        lessons.addAll(query.find());
    }
}