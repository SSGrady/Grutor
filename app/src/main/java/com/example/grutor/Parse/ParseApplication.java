package com.example.grutor.Parse;

import android.app.Application;

import com.example.grutor.Modals.Lessons;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: Register parse models

        // ParseObject.registerSubclass(bestAT.class);
        // ParseObject.registerSubclass(grade.class);
        ParseObject.registerSubclass(Lessons.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("44G4lceevcRvp0HJ8yammgYVuc797zfMNMNSEJOe")
                .clientKey("hm8Uh1GcvlYnuy5TMFNe7iv6umwi0MQw3vJvBurQ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
