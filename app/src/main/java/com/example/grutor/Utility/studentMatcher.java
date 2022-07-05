package com.example.grutor.Utility;

import com.example.grutor.Modals.Lessons;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class studentMatcher {
    public ParseUser currentUser;
    public String requestedLesson;

    // TODO implement bestAt matching with subject requested
    protected final String KEY_BEST_AT = "bestAt";
    protected String KEY_USER_OBJECT_ID;
    protected final String KEY_OBJECT_ID = "objectId";
    protected final String KEY_GRADE = "grade";
    protected String KEY_USERS_GRADE;
    public List<ParseUser> matches;

    public studentMatcher(Lessons requestedLesson){

        this.currentUser = ParseUser.getCurrentUser();
        matches = new ArrayList<>();
        KEY_USER_OBJECT_ID = String.valueOf(currentUser.getObjectId());
        KEY_USERS_GRADE  = String.valueOf(currentUser.get(KEY_GRADE));
        if (requestedLesson != null) {
            this.requestedLesson = requestedLesson.getTutoringSubject();
        }
    }

    public void getMyMatches() throws ParseException {
        ParseQuery <ParseUser> query =  ParseQuery.getQuery("_User");
        query.setLimit(5);
        query.whereEqualTo(KEY_GRADE, KEY_USERS_GRADE);
//        query.whereEqualTo(KEY_BEST_AT, requestedLesson);
        query.whereNotEqualTo(KEY_OBJECT_ID, KEY_USER_OBJECT_ID);
//        if (matches.size() != 0) {
//            for (int i = 0; i < matches.size(); i++) {
//                System.out.println(matches.get(i));
//            }
//            System.out.println("End of new matching algorithm matches!");
//        }
        matches.addAll(query.find());
    }
}
