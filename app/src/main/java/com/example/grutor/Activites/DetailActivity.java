package com.example.grutor.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.grutor.R;

public class DetailActivity extends AppCompatActivity {

    protected Spinner spDayOfWeek;
    protected Spinner spTopicsList;
    protected String desiredSubject = "";
    protected Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // hides action bar
        getSupportActionBar().hide();


        spDayOfWeek = findViewById(R.id.spDayOfWeek);
        spTopicsList = findViewById(R.id.spTopicsList);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            desiredSubject = bundle.getString("subject");
        }
        ArrayAdapter<CharSequence> dayOfWeekAdapter= ArrayAdapter.createFromResource(this, R.array.day_of_week, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> topicsListAdapter;

        topicsListAdapter = checkTopic(desiredSubject);

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