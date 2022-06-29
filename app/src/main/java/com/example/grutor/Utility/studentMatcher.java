package com.example.grutor.Utility;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class studentMatcher {
    public ParseUser currentUser;

    // TODO protected final String KEY_BEST_AT = "bestAt";
    protected String KEY_USER_OBJECT_ID;
    protected final String KEY_OBJECT_ID = "objectId";
    protected final String KEY_GRADE = "grade";
    protected String KEY_USERS_GRADE;
    public List<ParseUser> matches;

    public studentMatcher(){

        this.currentUser = ParseUser.getCurrentUser();
        matches = new ArrayList<>();
        KEY_USER_OBJECT_ID = String.valueOf(currentUser.getObjectId());
        KEY_USERS_GRADE  = String.valueOf(currentUser.get(KEY_GRADE));
    }

    public void getMyMatches() throws ParseException {
        ParseQuery <ParseUser> query =  ParseQuery.getQuery("_User");
        query.setLimit(5);
        query.whereEqualTo(KEY_GRADE, KEY_USERS_GRADE);
        query.whereNotEqualTo(KEY_OBJECT_ID, KEY_USER_OBJECT_ID);
        matches.addAll(query.find());
//        query.findInBackground(new FindCallback<ParseUser>() {
//            @Override
//            public void done(List<ParseUser> studentMatches, ParseException e) {
//                // System.out.println(studentMatches.size());
//                matches.addAll(studentMatches);
//            }
//        });
    }
}