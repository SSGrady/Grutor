package com.example.grutor.Utility;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.Modals.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class StudentMatcher{
    public User currentUser;
    public String requestedLessonString;
    public int rank;

    protected ParseQuery<ParseUser> query;
    protected final String KEY_BEST_AT = "bestAt";
    protected String KEY_USER_OBJECT_ID;
    protected final String KEY_OBJECT_ID = "objectId";
    protected final String KEY_GRADE = "grade";
    private final String KEY_AREA_CODE = "zipcode";
    private final String KEY_CITY_CODE = "cityName";
    protected String KEY_USERS_GRADE;
    public List<ParseUser> matches;

    public StudentMatcher(String requestedLessonString){
        super();
        this.currentUser = (User) ParseUser.getCurrentUser();
        matches = new ArrayList<>();
        KEY_USER_OBJECT_ID = String.valueOf(currentUser.getObjectId());
        KEY_USERS_GRADE  = String.valueOf(currentUser.get(KEY_GRADE));
        this.requestedLessonString = requestedLessonString;
        query =  ParseQuery.getQuery("_User");
    }

    public void queryByCity() {
        query.whereEqualTo(KEY_CITY_CODE, currentUser.getCity());
        query.whereEqualTo(KEY_BEST_AT, requestedLessonString);
    }

    // scope of a zip code is potentially farther in distance between students and student tutors
    // in other words, postal zip code may not represent the same city that users reside in.
    public void queryByAreaCode() {
            query.whereEqualTo(KEY_AREA_CODE, currentUser.getZipcode());
            query.whereEqualTo(KEY_BEST_AT, requestedLessonString);
    }

    public void getMyMatches() throws ParseException {
        List<ParseUser> studentTutors = new ArrayList<>();

        //setup 1
        queryByCity();
        studentTutors.addAll(query.find());
        // setup2
        if (studentTutors.size() < 5) {
            queryByAreaCode();
            query.whereEqualTo(KEY_GRADE, KEY_USERS_GRADE);
        }

        if (!(studentTutors.size() > 5)) {
            query =  ParseQuery.getQuery("_User");
            query.whereEqualTo(KEY_BEST_AT, requestedLessonString);
            query.whereEqualTo(KEY_BEST_AT, requestedLessonString);
        }

        query.whereNotEqualTo(KEY_OBJECT_ID, KEY_USER_OBJECT_ID);
        query.setLimit(5);
        matches.addAll(query.find());
    }
}
