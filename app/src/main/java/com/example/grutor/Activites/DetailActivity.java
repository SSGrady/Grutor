package com.example.grutor.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grutor.Fragments.DatePickerFragment;
import com.example.grutor.Fragments.LessonsFragment;
import com.example.grutor.Fragments.MessagesFragment;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    protected Lessons lesson;
    protected ParseUser currentUser;
    protected Spinner spTopicsList;
    protected String desiredSubject = "";
    protected Bundle bundle;
    protected Button btnConfirm, btnHw, btnExam, btnEssay, btnOther;
    protected ImageButton ibCalendar;
    protected TextView tvProblem1, tvProblem2, tvProblem3, tvDateSelected;
    protected EditText etDescription;
    protected boolean isColor, isTextVisible;
    public static int KEY_BLUEBLACK, KEY_BUTTONPRIMARY, KEY_BLUEBLACK_LIGHT;
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
        btnConfirm = findViewById(R.id.btnConfirm);
        ArrayAdapter<CharSequence> topicsListAdapter;
        lesson = new Lessons();
        currentUser = ParseUser.getCurrentUser();
        topicsListAdapter = checkTopic(desiredSubject);
        tvProblem1 = findViewById(R.id.tvProblem1);
        tvProblem2 = findViewById(R.id.tvProblem2);
        tvProblem3 = findViewById(R.id.tvProblem3);
        tvDateSelected = findViewById(R.id.tvDateSelected);
        btnHw = findViewById(R.id.btnHw);
        btnExam = findViewById(R.id.btnExam);
        btnEssay = findViewById(R.id.btnEssay);
        btnOther = findViewById(R.id.btnOther);
        ibCalendar = findViewById(R.id.ibCalendar);
        etDescription = findViewById(R.id.etDescription);
        isColor = true;
        isTextVisible = true;
        topicsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spTopicsList.setAdapter(topicsListAdapter);

        createColors();
        btnChangeDisplay();
        tvChangeDisplay();

        spTopicsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spTopicsList.getSelectedItem().toString().equals("Subject")) {
                    TOPIC_KEY = spTopicsList.getSelectedItem().toString();
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
            @Override
            public void onClick(View v) {
                lessonRequest();
            }
        });

    }

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

    private void tvChangeDisplay() {
        if (isTextVisible) {
            tvProblem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvProblem2.setBackgroundColor(KEY_BLUEBLACK_LIGHT);
                    tvProblem3.setBackgroundColor(KEY_BLUEBLACK_LIGHT);
                    tvProblem1.setBackgroundColor(KEY_BLUEBLACK);
                    NUM_PROBLEM_KEY = trimProblemCount(tvProblem1.getText().toString());
                }
            });
            tvProblem2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvProblem1.setBackgroundColor(KEY_BLUEBLACK_LIGHT);
                    tvProblem3.setBackgroundColor(KEY_BLUEBLACK_LIGHT);
                    tvProblem2.setBackgroundColor(KEY_BLUEBLACK);
                    NUM_PROBLEM_KEY = trimProblemCount(tvProblem2.getText().toString());
                }
            });
            tvProblem3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvProblem1.setBackgroundColor(KEY_BLUEBLACK_LIGHT);
                    tvProblem2.setBackgroundColor(KEY_BLUEBLACK_LIGHT);
                    tvProblem3.setBackgroundColor(KEY_BLUEBLACK);
                    NUM_PROBLEM_KEY = trimProblemCount(tvProblem3.getText().toString());
                }
            });
        }
        else {
            // reset the textviews and the key
            tvProblem1.setBackgroundColor(KEY_BLUEBLACK_LIGHT);
            tvProblem2.setBackgroundColor(KEY_BLUEBLACK_LIGHT);
            tvProblem3.setBackgroundColor(KEY_BLUEBLACK_LIGHT);
            NUM_PROBLEM_KEY = "";
        }
    }

    private String trimProblemCount(String s) {
        String editStr= s.trim();
        s = editStr.replaceAll("\n"," ");
        return s;
    }

    @SuppressLint("NewApi")
    private void createColors() {
        KEY_BLUEBLACK = getColor(R.color.blueBlack);
        KEY_BUTTONPRIMARY = getColor(R.color.BEA_blue_grotto);
        KEY_BLUEBLACK_LIGHT = getColor(R.color.blueBlack_light);
    }

    private void btnChangeDisplay() {
        // Default
        tvProblem1.setVisibility(View.GONE);
        tvProblem2.setVisibility(View.GONE);
        tvProblem3.setVisibility(View.GONE);

        btnHw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isColor) {
                    btnExam.setBackgroundColor(KEY_BUTTONPRIMARY);
                    btnEssay.setBackgroundColor(KEY_BUTTONPRIMARY);
                    btnOther.setBackgroundColor(KEY_BUTTONPRIMARY);
                }
                btnHw.setBackgroundColor(KEY_BLUEBLACK);
                tvProblem1.setVisibility(View.VISIBLE);
                tvProblem2.setVisibility(View.VISIBLE);
                tvProblem3.setVisibility(View.VISIBLE);
                TYPE_OF_TUTORING_KEY = btnHw.getText().toString();
            }
        });
        btnExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isColor) {
                    btnHw.setBackgroundColor(KEY_BUTTONPRIMARY);
                    btnEssay.setBackgroundColor(KEY_BUTTONPRIMARY);
                    btnOther.setBackgroundColor(KEY_BUTTONPRIMARY);
                }
                btnExam.setBackgroundColor(KEY_BLUEBLACK);
                tvProblem1.setVisibility(View.VISIBLE);
                tvProblem2.setVisibility(View.VISIBLE);
                tvProblem3.setVisibility(View.VISIBLE);
                TYPE_OF_TUTORING_KEY = btnExam.getText().toString();
            }
        });
        btnEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isColor) {
                    btnHw.setBackgroundColor(KEY_BUTTONPRIMARY);
                    btnExam.setBackgroundColor(KEY_BUTTONPRIMARY);
                    btnOther.setBackgroundColor(KEY_BUTTONPRIMARY);
                }
                btnEssay.setBackgroundColor(KEY_BLUEBLACK);
                tvProblem1.setVisibility(View.GONE);
                tvProblem2.setVisibility(View.GONE);
                tvProblem3.setVisibility(View.GONE);
                TYPE_OF_TUTORING_KEY = btnEssay.getText().toString();
                isTextVisible = false;
                tvChangeDisplay();
            }
        });
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isColor) {
                    btnExam.setBackgroundColor(KEY_BUTTONPRIMARY);
                    btnEssay.setBackgroundColor(KEY_BUTTONPRIMARY);
                    btnHw.setBackgroundColor(KEY_BUTTONPRIMARY);
                }
                btnOther.setBackgroundColor(KEY_BLUEBLACK);
                tvProblem1.setVisibility(View.GONE);
                tvProblem2.setVisibility(View.GONE);
                tvProblem3.setVisibility(View.GONE);
                TYPE_OF_TUTORING_KEY = btnOther.getText().toString();
                isTextVisible = false;
                tvChangeDisplay();
            }
        });
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