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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Activites.LoginActivity;
import com.example.grutor.Adapters.SubjectAdapter;
import com.example.grutor.R;
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
    protected ParseUser user;
    protected String welcomeMessage = "";
    public RecyclerView rvSubjects;
    private List<String> titles;
    private List<Integer> images;
    protected SubjectAdapter adapter;
    protected  GridLayoutManager gridLayoutManager;
    protected QueryUtils jsonWeatherHandler;
    protected ImageView ivWeather;
    protected WeatherHour currentForecast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        tvWelcomeUser = view.findViewById(R.id.tvWelcomeUser);
        // Recycler View population.
        rvSubjects = view.findViewById(R.id.rvSubjects);
        titles = new ArrayList<>();
        images = new ArrayList<>();
        ivWeather = view.findViewById(R.id.ivWeather);
        jsonWeatherHandler = new QueryUtils();
        setWelcomeMessage();

        createCard();

        rvSubjects.setLayoutManager(gridLayoutManager);
        rvSubjects.setAdapter(adapter);

        user = ParseUser.getCurrentUser();

    }

    private void setWelcomeMessage() {
        jsonWeatherHandler.getHomeMessage(getContext(), new JsonHttpResponseHandler() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    currentForecast = jsonWeatherHandler.extractFeatureFromJson(json.jsonObject);
                    // note the emulator might have the wrong default time.
                    Date today = new Date();
                    int timeOfDay = today.toInstant().atZone(ZoneId.of(currentForecast.getTimeZoneString())).toLocalDateTime().getHour();
                    //LocalDate t2 = LocalDate.now().
                    String greeting = "";
                    int res = 0;
                    if(timeOfDay>=7 && timeOfDay<12){
                        greeting = "Good Morning";
                        res = R.drawable.icon8_morning_icon;
                    } else if(timeOfDay>=12 && timeOfDay<16){
                        greeting = "Good Afternoon";
                        res = R.drawable.icons8_sun_64;
                    } else if(timeOfDay>=16 && timeOfDay<21){
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
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
    }

    private void createCard() {
        titles.add(getString(R.string.math));
        titles.add(getString(R.string.english));
        titles.add(getString(R.string.science));
        titles.add(getString(R.string.history));
        titles.add(getString(R.string.government));
        titles.add(getString(R.string.economics));

        images.add(R.drawable.icons8_math_64);
        images.add(R.drawable.icons8_english_64);
        images.add(R.drawable.icons8_physics_64);
        images.add(R.drawable.icons8_history_64);
        images.add(R.drawable.icons8_government_64);
        images.add(R.drawable.icons8_stock_share_64);

        adapter = new SubjectAdapter(getContext(), titles, images);
        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
    }
}