package com.example.grutor.Fragments;

import static com.parse.Parse.getApplicationContext;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    protected studentMatcher matching;
    RecyclerView rvLessons, rvMatches;
    LessonAdapter lessonsAdapter;
    MatchesAdapter matcher;
    ArrayList<Lessons> lessons;
    Button btnMatch;

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
    }

    private void queryLessons() throws ParseException {
        ParseQuery<Lessons> query = ParseQuery.getQuery(Lessons.class);
        query.setLimit(5);
        lessons.addAll(query.find());
    }
}