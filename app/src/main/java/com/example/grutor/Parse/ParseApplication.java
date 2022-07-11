package com.example.grutor.Parse;

import android.app.Application;

import com.example.grutor.Modals.Groupchat;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.Modals.Message;
import com.example.grutor.Modals.User;
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
                .applicationId("44G4lceevcRvp0HJ8yammgYVuc797zfMNMNSEJOe")
                .clientKey("hm8Uh1GcvlYnuy5TMFNe7iv6umwi0MQw3vJvBurQ")
                .server("https://parseapi.back4app.com/")
                .build()
        );
        ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
    }
}
