package com.example.grutor.Activites;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.grutor.Fragments.HomeFragment;
import com.example.grutor.Fragments.LessonsFragment;
import com.example.grutor.Fragments.MessagesFragment;
import com.example.grutor.Fragments.ProfileFragment;
import com.example.grutor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.parse.ParseUser;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class FeedActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    final Fragment fragmentHome = new HomeFragment();
    final Fragment fragmentLessons = new LessonsFragment();
    final Fragment fragmentProfile = new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        if (ParseUser.getCurrentUser().get("isNewUser").equals(true)) {
            Toasty.success(this, "Welcome " + ParseUser.getCurrentUser().getUsername() + "!", Toast.LENGTH_SHORT, true).show();
            final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icons8_sun_64);
            Shape drawableShape = new Shape.DrawableShape(drawable, true);
            final KonfettiView konfettiView = findViewById(R.id.konfettiView);
            EmitterConfig emitterConfig = new Emitter(5L, TimeUnit.SECONDS).perSecond(50);
            Party party = new PartyFactory(emitterConfig)
                    .angle(270)
                    .spread(90)
                    .setSpeedBetween(2f, 8f)
                    .timeToLive(2000L)
                    .shapes(new Shape.Rectangle(0.2f), drawableShape)
                    .position(0.0, 0.0, 1.0, 0.0)
                    .build();
            konfettiView.start(party);
            ParseUser.getCurrentUser().put("isNewUser", false);
            ParseUser.getCurrentUser().saveInBackground();
        }

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