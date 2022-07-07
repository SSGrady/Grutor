package com.example.grutor.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.grutor.Fragments.HomeFragment;
import com.example.grutor.Fragments.LessonsFragment;
import com.example.grutor.Fragments.MessagesFragment;
import com.example.grutor.Fragments.ProfileFragment;
import com.example.grutor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FeedActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    final Fragment fragmentHome = new HomeFragment();
    final Fragment fragmentLessons = new LessonsFragment();
    final Fragment fragmentProfile = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // hides action bar
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        fragment = fragmentProfile;
                        break;
                    case R.id.action_lessons:
                        fragment = fragmentLessons;
                        break;
                    case R.id.action_home:
                        fragment = fragmentHome;
                    default:
                        break;
                }
                assert fragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}