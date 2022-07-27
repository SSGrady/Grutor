package com.example.grutor.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grutor.Fragments.DatePickerFragment;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    protected Lessons lesson;
    protected ParseUser currentUser;
    protected Spinner spTopicsList;
    protected String desiredSubject = "";
    protected Bundle bundle;
    protected AppCompatButton btnConfirm;
    protected ImageButton ibCalendar;
    private TextView tvDateSelected;
    protected EditText etDescription;
    private ChipGroup cgType, cgNumProblem;
    private Chip chip, chip2;
    private boolean hasTopic;
    public String NUM_PROBLEM_KEY, TYPE_OF_TUTORING_KEY, TUTORING_DESCRIPTION_KEY, URGENCY_KEY, TOPIC_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            desiredSubject = bundle.getString("subject");
        }

        spTopicsList = findViewById(R.id.spTopicsList);
        btnConfirm = findViewById(R.id.abtnConfirm);
        ArrayAdapter<CharSequence> topicsListAdapter;
        lesson = new Lessons();
        currentUser = ParseUser.getCurrentUser();
        topicsListAdapter = checkTopic(desiredSubject);
        tvDateSelected = findViewById(R.id.tvDateSelected);
        ibCalendar = findViewById(R.id.ibCalendar);
        etDescription = findViewById(R.id.etDescription);
        URGENCY_KEY = "";
        hasTopic = false;
        cgType = findViewById(R.id.cgType);
        cgNumProblem = findViewById(R.id.cgNumProblem);
        topicsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spTopicsList.setAdapter(topicsListAdapter);

        cgType.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                setNumberOfTutoringProblems(group);
            }
        });

        spTopicsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!spTopicsList.getSelectedItem().toString().equals("Topic")) {
                    TOPIC_KEY = spTopicsList.getSelectedItem().toString();
                    hasTopic = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        ibCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                if ((URGENCY_KEY.length() > 1 && hasTopic)) {
                    if ((TYPE_OF_TUTORING_KEY.equals("Homework") || TYPE_OF_TUTORING_KEY.equals("Exam")) && !NUM_PROBLEM_KEY.isEmpty())
                        lessonRequest();
                    // if the number of problems is an optional field
                    else if ((TYPE_OF_TUTORING_KEY.equals("Essay") || TYPE_OF_TUTORING_KEY.equals("Other")) && NUM_PROBLEM_KEY.isEmpty())
                        lessonRequest();
                    else
                        Snackbar.make(findViewById(R.id.clDetails), R.string.tutoring_request, Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(findViewById(R.id.clDetails), R.string.tutoring_request, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @SuppressLint("CheckResult")
    private void lessonRequest() {
        bundle.putString("type", TYPE_OF_TUTORING_KEY);
        bundle.putString("problem", NUM_PROBLEM_KEY);
        bundle.putString("urgency", URGENCY_KEY);
        bundle.putString("topic", TOPIC_KEY);
        // Description should be stored after clicking the confirm button.
        TUTORING_DESCRIPTION_KEY = etDescription.getText().toString();
        bundle.putString("description", TUTORING_DESCRIPTION_KEY);
        lesson.setTutoringSubject(desiredSubject);
        lesson.setTypeOfLesson(bundle.getString("type"));
        lesson.setAssignmentLength(bundle.getString("problem"));
        lesson.setCalendarDate(bundle.getString("urgency"));
        lesson.setTutoringTopic(bundle.getString("topic"));
        lesson.setTutoringDescription(bundle.getString("description"));
        lesson.setStudent(currentUser);
        lesson.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(DetailActivity.this, "Error while saving Lesson.", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(DetailActivity.this, FeedActivity.class);
                i.putExtras(bundle);
                finish();
            }
        });
    }

    private void setNumberOfTutoringProblems(ChipGroup group) {
        chip = group.findViewById(group.getCheckedChipId());
        if ( chip != null) {
            TYPE_OF_TUTORING_KEY = chip.getText().toString();
            if (chip.getText().toString().equals("Homework") || chip.getText().toString().equals("Exam")) {
                cgNumProblem.setVisibility(View.VISIBLE);
                // this initialization is for edge case where user selects Homework or Exam then clicks the Confirm button
                NUM_PROBLEM_KEY = "";
                cgNumProblem.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
                    @Override
                    public void onCheckedChanged(@NonNull ChipGroup group2, @NonNull List<Integer> checkedIds) {
                        chip2 = group2.findViewById(group2.getCheckedChipId());
                        if (chip2 != null)
                            NUM_PROBLEM_KEY = chip2.getText().toString();
                        else
                            NUM_PROBLEM_KEY = "";
                    }
                });
            } else {
                if (chip2 != null)
                    chip2.setChecked(false);
                cgNumProblem.setVisibility(View.GONE);
                NUM_PROBLEM_KEY = "";
            }
        } else {
            cgNumProblem.setVisibility(View.GONE);
        }
    }

    private ArrayAdapter<CharSequence> checkTopic(String desiredSubject) {
        ArrayAdapter<CharSequence> topicsListAdapter;
        switch (desiredSubject) {
            case "Math":
                topicsListAdapter = ArrayAdapter.createFromResource(this, R.array.topic_math, android.R.layout.simple_spinner_item);
                break;
            case "Science":
                topicsListAdapter = ArrayAdapter.createFromResource(this, R.array.topic_science, android.R.layout.simple_spinner_item);
                break;
            case "History":
                topicsListAdapter = ArrayAdapter.createFromResource(this, R.array.topic_history, android.R.layout.simple_spinner_item);
                break;
            case "Government":
                topicsListAdapter = ArrayAdapter.createFromResource(this, R.array.topic_government, android.R.layout.simple_spinner_item);
                break;
            case "Economics":
                topicsListAdapter = ArrayAdapter.createFromResource(this, R.array.topic_economics, android.R.layout.simple_spinner_item);
                break;

            default:
                topicsListAdapter = ArrayAdapter.createFromResource(this, R.array.topic_english, android.R.layout.simple_spinner_item);
                break;
        }
        return topicsListAdapter;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        try{
            view.updateDate(year, month, dayOfMonth);
        }catch (Exception ignored){}

        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tvDateSelected.setText(currentDateString);
        URGENCY_KEY = tvDateSelected.getText().toString();
    }
}