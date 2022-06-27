package com.example.grutor.Activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.grutor.R;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    protected Spinner spDayOfWeek;
    protected Spinner spTopicsList;
    protected String desiredSubject = "";
    protected Bundle bundle;
    protected Button btnConfirm;
    protected Button btnHw;
    protected Button btnExam;
    protected Button btnEssay;
    protected Button btnOther;
    protected TextView tvProblem1;
    protected TextView tvProblem2;
    protected TextView tvProblem3;
    protected boolean isColor;
    public int KEY_BLUEBLACK;
    public int KEY_BUTTONPRIMARY;
    public int KEY_BLUEBLACK_LIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // hides action bar
        getSupportActionBar().hide();

        spDayOfWeek = findViewById(R.id.spDayOfWeek);
        spTopicsList = findViewById(R.id.spTopicsList);
        btnConfirm = findViewById(R.id.btnConfirm);
        bundle = getIntent().getExtras();

        if (bundle != null) {
            desiredSubject = bundle.getString("subject");
        }
        ArrayAdapter<CharSequence> dayOfWeekAdapter= ArrayAdapter.createFromResource(this, R.array.day_of_week, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> topicsListAdapter;

        topicsListAdapter = checkTopic(desiredSubject);
        tvProblem1 = findViewById(R.id.tvProblem1);
        tvProblem2 = findViewById(R.id.tvProblem2);
        tvProblem3 = findViewById(R.id.tvProblem3);
        btnHw = findViewById(R.id.btnHw);
        btnExam = findViewById(R.id.btnExam);
        btnEssay = findViewById(R.id.btnEssay);
        btnOther = findViewById(R.id.btnOther);
        isColor = true;

        createColors();
        btnChangeDisplay();

        dayOfWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        topicsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spDayOfWeek.setAdapter(dayOfWeekAdapter);
        spTopicsList.setAdapter(topicsListAdapter);

        spDayOfWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spDayOfWeek.getSelectedItem().toString().equals("Pick a Day")) {
                    bundle.putString("day", spDayOfWeek.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spTopicsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spTopicsList.getSelectedItem().toString().equals("Subject")) {
                    bundle.putString("topic", spTopicsList.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Populate the Lessons fragment with new data.
                // grab the new data of the Users tutoring session details
                bundle = getIntent().getExtras();
                // TODO: Fire an intent from the Detail Activity to the Feed Activity
            }
        });
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
            default:
                topicsListAdapter = ArrayAdapter.createFromResource(this, R.array.topic_economics, android.R.layout.simple_spinner_item);
                break;
        }
        return topicsListAdapter;
    }
}