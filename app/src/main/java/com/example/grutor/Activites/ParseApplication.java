package com.example.grutor.Activites;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: Register your parse models

        // ParseObject.registerSubclass(Post.class);
        // ParseObject.registerSubclass(User.class);
        // ParseObject.registerSubclass(Comment.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("44G4lceevcRvp0HJ8yammgYVuc797zfMNMNSEJOe")
                .clientKey("hm8Uh1GcvlYnuy5TMFNe7iv6umwi0MQw3vJvBurQ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
