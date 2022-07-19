package com.example.grutor.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.grutor.Adapters.OnboardingAdapter;
import com.example.grutor.R;
import com.example.grutor.Utility.OnboardingItem;
import com.google.android.material.button.MaterialButton;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class OnboardingActivity extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        getSupportActionBar().hide();

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupOnboardingItems();
        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                } else {
                        celebrateOnboarding();
                    }
                 }
        });
    }

    private void celebrateOnboarding() {
        final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icons8_sun_64);
        Shape drawableShape = new Shape.DrawableShape(drawable, true);
        final KonfettiView konfettiView = findViewById(R.id.konfettiView);
        EmitterConfig emitterConfig = new Emitter(5L, TimeUnit.SECONDS).perSecond(50);
        Party party = new PartyFactory(emitterConfig)
                .angle(270)
                .spread(90)
                .setSpeedBetween(2f, 8f)
                .timeToLive(1000L)
                .shapes(new Shape.Rectangle(0.2f), drawableShape)
                .position(0.0, 0.0, 1.0, 0.0)
                .build();
        konfettiView.start(party);
        new CountDownTimer(4800, 800) {

            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                finish();
            }
        }.start();
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemSubjectCards = new OnboardingItem();
        itemSubjectCards.setTitle("Choose The Subject You Need Help With");
        // itemPayOnline.setDescription("From Math & Science, to History, English, Government and More!");
        itemSubjectCards.setImage(R.drawable.onboard_lesson_request);

        OnboardingItem itemLessonDetails = new OnboardingItem();
        itemLessonDetails.setTitle("Add Lesson Details And Tap Confirm");
        // itemOnTheWay.setDescription("Simply add details and tap confirm");
        itemLessonDetails.setImage(R.drawable.onboard_request_help);

        OnboardingItem itemMatchWithStudents = new OnboardingItem();
        itemMatchWithStudents.setTitle("Match With Other Students");
        // itemEatTogether.setDescription("Learn from students who get it 24/7. Message them and don't be shy!");
        itemMatchWithStudents.setImage(R.drawable.onboarding_matching);

        OnboardingItem itemMessageStudents = new OnboardingItem();
        itemMessageStudents.setTitle("Learn From Students Who Get It 24/7");
        // itemEatTogether.setDescription("Learn from students who get it 24/7. Message them and don't be shy!");
        itemMessageStudents.setImage(R.drawable.onboard_messaging);

        onboardingItems.add(itemSubjectCards);
        onboardingItems.add(itemLessonDetails);
        onboardingItems.add(itemMatchWithStudents);
        onboardingItems.add(itemMessageStudents);
        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8 ,0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicator(int index) {
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            }
            if (index == onboardingAdapter.getItemCount() - 1) {
                buttonOnboardingAction.setText("Start");
            } else {
                buttonOnboardingAction.setText("Next");
            }
        }
    }
}