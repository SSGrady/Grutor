package com.example.grutor.Parse;

import android.app.Application;

import com.example.grutor.Modals.Groupchat;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.Modals.Message;
import com.example.grutor.Modals.User;
import com.example.grutor.R;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.livequery.ParseLiveQueryClient;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Lessons.class);
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(Groupchat.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.parse_application_id))
                .clientKey(getString(R.string.parse_client_key))
                .server("https://parseapi.back4app.com/")
                .build()
        );
        ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
    }
}
