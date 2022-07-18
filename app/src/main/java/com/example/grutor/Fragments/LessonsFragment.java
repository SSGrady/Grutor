package com.example.grutor.Fragments;

import static androidx.core.content.ContextCompat.getColor;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.grutor.Adapters.LessonAdapter;
import com.example.grutor.Adapters.MatchesAdapter;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.example.grutor.Utility.SwipeToDeleteCallback;
import com.example.grutor.Utility.StudentMatcher;
import com.google.android.material.snackbar.Snackbar;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class LessonsFragment extends Fragment {
    private static final String KEY_QUERY_BY_STUDENT = "student";
    private static final String KEY_QUERY_BY_STUDENT_TUTOR = "studentTutor";
    private static final String KEY_CREATED_AT_QUERY = "createdAt";
    protected StudentMatcher matching;
    RecyclerView rvLessons, rvMatches;
    LessonAdapter lessonsAdapter;
    MatchesAdapter matcher;
    ArrayList<Lessons> lessons;
    Button btnMatch;
    FrameLayout flLessons;

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
        flLessons = view.findViewById(R.id.flLessons);
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
                matching = new StudentMatcher(lessonsAdapter.requestedLessonString);
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
        enableSwipeToDeleteAndUndo();
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Lessons lesson = lessonsAdapter.getData().get(position);

                lessonsAdapter.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(flLessons, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        lessonsAdapter.restoreItem(lesson, position);
                        rvLessons.scrollToPosition(position);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvLessons);
    }


    private void queryLessons() throws ParseException {
        ParseQuery<Lessons> students = ParseQuery.getQuery(Lessons.class);
        ParseQuery<Lessons> tutors = ParseQuery.getQuery(Lessons.class);
        students.whereEqualTo(KEY_QUERY_BY_STUDENT, ParseUser.getCurrentUser());
        tutors.whereEqualTo(KEY_QUERY_BY_STUDENT_TUTOR, ParseUser.getCurrentUser());
        List<ParseQuery<Lessons>> queries = new ArrayList<ParseQuery<Lessons>>();
        queries.add(students);
        queries.add(tutors);
        ParseQuery<Lessons> mainQuery = ParseQuery.or(queries);
        mainQuery.setLimit(5);
        mainQuery.orderByDescending(KEY_CREATED_AT_QUERY);
        lessons.addAll(mainQuery.find());
    }
}