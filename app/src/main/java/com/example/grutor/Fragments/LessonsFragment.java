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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Adapters.LessonAdapter;
import com.example.grutor.Adapters.MatchesAdapter;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.example.grutor.Utility.SwipeToDeleteCallback;
import com.example.grutor.Utility.StudentMatcher;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import dots.animation.textview.DotAnimatedTextView;

public class LessonsFragment extends Fragment implements FeedActivity.onLessonChangedListener, FeedActivity.onMatchAcceptedListener{
    private static final String KEY_QUERY_BY_STUDENT = "student";
    private static final String KEY_QUERY_BY_STUDENT_TUTOR = "studentTutor";
    private static final String KEY_CREATED_AT_QUERY = "createdAt";
    protected FeedActivity instance;
    protected StudentMatcher matching;
    RecyclerView rvLessons, rvMatches;
    LessonAdapter lessonsAdapter, studentAdapter, tutorAdapter;
    MatchesAdapter matcher;
    ArrayList<Lessons> studentLessons, tutorLessons;
    TabLayout tablLesssons;
    TextView tvLessonsTitle;
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
        rvLessons = view.findViewById(R.id.rvLessons);
        rvMatches = view.findViewById(R.id.rvMatches);
        flLessons = view.findViewById(R.id.flLessons);
        tvLessonsTitle = view.findViewById(R.id.tvLessonsTitle);
        tablLesssons = view.findViewById(R.id.tablLessons);
        studentLessons = new ArrayList<>();
        tutorLessons = new ArrayList<>();
        rvLessons.setLayoutManager(new LinearLayoutManager(getContext()));
        studentAdapter = new LessonAdapter(getContext(), studentLessons);
        tutorAdapter = new LessonAdapter(getContext(), tutorLessons);
        instance = (FeedActivity) getContext();
        instance.setOnLessonChangedListener(this);
        instance.setOnMatchedListener(this);
        try {
            queryStudentLessons();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lessonsAdapter = new LessonAdapter(getContext(), studentLessons);
        rvLessons.setAdapter(lessonsAdapter);
        tablLesssons.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals("Student")) {
                    try {
                        queryStudentLessons();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tvLessonsTitle.setText(R.string.your_lessons);
                    lessonsAdapter = new LessonAdapter(getContext(), studentLessons);
                    rvLessons.setAdapter(lessonsAdapter);
                }
                else if (tab.getText().toString().equals("Tutor")) {
                    // remove the matching results from tutor view
                    studentLessons.clear();
                    studentAdapter = new LessonAdapter(getContext(), studentLessons);
                    rvMatches.setAdapter(studentAdapter);
                    // switch the LessonAdapter to the tutor view from student view
                    lessonsAdapter = new LessonAdapter(getContext(), tutorLessons);
                    try {
                        queryTutorLessons();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tvLessonsTitle.setText(R.string.teach_lessons);
                    rvLessons.setAdapter(lessonsAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        enableSwipeToDeleteAndUndo();
    }

    // function that matches a student with a student tutor for the correct or desired lesson,
    // rather than match with current and previous lessons
    public void doMatchStudents() {
        if (instance.requestedLesson != null) {
            matching = new StudentMatcher(lessonsAdapter.requestedLessonString);
            try {
                matching.getMyMatches();
                matcher = new MatchesAdapter(getContext(), matching.matches, lessonsAdapter.requestedLesson);
                matcher.notifyDataSetChanged();
                rvMatches.setAdapter(matcher);
                rvMatches.setLayoutManager(new LinearLayoutManager(getContext()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
                snackbar.setAnchorView(instance.bottomNavigationView);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvLessons);
    }

    private void queryStudentLessons() throws ParseException {
        ParseQuery<Lessons> students = ParseQuery.getQuery(Lessons.class);
        students.whereEqualTo(KEY_QUERY_BY_STUDENT, ParseUser.getCurrentUser());
        students.setLimit(5);
        students.orderByDescending(KEY_CREATED_AT_QUERY);
        studentLessons.clear();
        studentLessons.addAll(students.find());
    }
    private void queryTutorLessons() throws ParseException {
        ParseQuery<Lessons> tutors = ParseQuery.getQuery(Lessons.class);
        tutors.whereEqualTo(KEY_QUERY_BY_STUDENT_TUTOR, ParseUser.getCurrentUser());
        tutors.setLimit(5);
        tutors.orderByDescending(KEY_CREATED_AT_QUERY);
        tutorLessons.clear();
        tutorLessons.addAll(tutors.find());
    }

    @Override
    public void onLessonChanged(@NonNull Lessons lesson) {
        doMatchStudents();
    }

    @Override
    public void onMatched(@NonNull Lessons lesson) {
        if (lesson.getStudentTutor() == null) {
            lessonsAdapter.makeMatchButtonVisible(instance.holder);
            return;
        }
        lessonsAdapter.setMatchStatus(instance.holder, lesson);
        lessonsAdapter.notifyDataSetChanged();
    }
}