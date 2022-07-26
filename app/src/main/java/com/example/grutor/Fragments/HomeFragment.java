package com.example.grutor.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Activites.LoginActivity;
import com.example.grutor.Activites.OnboardingActivity;
import com.example.grutor.Adapters.SubjectAdapter;
import com.example.grutor.Modals.User;
import com.example.grutor.R;
import com.example.grutor.Utility.OnboardingItem;
import com.example.grutor.Utility.QueryUtils;
import com.example.grutor.Utility.WeatherHour;
import com.parse.Parse;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import okhttp3.Headers;


public class HomeFragment extends Fragment {

    public TextView tvWelcomeUser;
    protected User user;
    protected String welcomeMessage = "";
    public RecyclerView rvSubjects;
    private List<String> titles;
    private List<Integer> images;
    protected SubjectAdapter adapter;
    protected  GridLayoutManager gridLayoutManager;
    protected QueryUtils jsonWeatherHandler;
    protected ImageView ivWeather;
    private ImageButton ibOnboardingHelp;
    protected WeatherHour currentForecast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvWelcomeUser = view.findViewById(R.id.tvWelcomeUser);
        ibOnboardingHelp = view.findViewById(R.id.ibOnboardingHelp);
        // Recycler View population.
        rvSubjects = view.findViewById(R.id.rvSubjects);
        titles = new ArrayList<>();
        images = new ArrayList<>();
        ivWeather = view.findViewById(R.id.ivWeather);
        jsonWeatherHandler = new QueryUtils();
        user = (User) ParseUser.getCurrentUser();
        setWelcomeMessage();

        createCard();

        rvSubjects.setLayoutManager(gridLayoutManager);
        rvSubjects.setAdapter(adapter);

        ibOnboardingHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent((FeedActivity) getContext(), OnboardingActivity.class);
                startActivity(i);
            }
        });


    }

    private void setWelcomeMessage() {
        jsonWeatherHandler.getHomeMessage(getContext(), new JsonHttpResponseHandler() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    currentForecast = jsonWeatherHandler.extractFeatureFromJson(json.jsonObject);
                    // checks for null first since it will short-circuit if it is null
                    if (user.getCity() == null || (user.getCity().isEmpty())) {
                        user.setCity(currentForecast.getCityName());
                        user.saveInBackground();
                    }
                    // note the emulator might have the wrong default time.
                    Date today = new Date();
                    int timeOfDay = today.toInstant().atZone(ZoneId.of(currentForecast.getTimeZoneString())).toLocalDateTime().getHour();
                    //LocalDate t2 = LocalDate.now().
                    String greeting = "";
                    int res = 0;
                    if(timeOfDay>=7 && timeOfDay<12){
                        greeting = "Good Morning";
                        res = R.drawable.icon8_morning_icon;
                    } else if(timeOfDay>=12 && timeOfDay<17){
                        greeting = "Good Afternoon";
                        res = R.drawable.icons8_sun_64;
                    } else if(timeOfDay>=17 && timeOfDay<21){
                        greeting = "Good Evening";
                        res = R.drawable.icons8_dusk_64;
                    } else if((timeOfDay>=21 && timeOfDay<=24) || (timeOfDay>=1 && timeOfDay<7)){
                        greeting = "Good Night";
                        res = R.drawable.icons8_night_65;
                    }
                    welcomeMessage = String.format("%s %s!", greeting, user.getUsername());
                    tvWelcomeUser.setText(welcomeMessage);
                    ivWeather.setBackgroundResource(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {}
        });
    }

    private void createCard() {
        titles.add(getString(R.string.math));
        titles.add(getString(R.string.english));
        titles.add(getString(R.string.science));
        titles.add(getString(R.string.history));
        titles.add(getString(R.string.government));
        titles.add(getString(R.string.economics));

        adapter = new SubjectAdapter(getContext(), titles);
        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
    }
}