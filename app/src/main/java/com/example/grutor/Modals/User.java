package com.example.grutor.Modals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PARSE_FILE = "profilePhoto";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_AREA_CODE = "zipcode";
    public static final String KEY_CITY_NAME = "cityName";

    public String getUsername() {
        try {
            return fetchIfNeeded().getString(KEY_USERNAME);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getString(KEY_USERNAME);
    }
    public void setUsername(String username) {put(KEY_USERNAME, username);}
    @Nullable
    public ParseFile getParseFile(@NonNull String key) {
        Object value = null;
        try {
            value = fetchIfNeeded().get(key);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!(value instanceof ParseFile)) {
            return null;
        }
        return (ParseFile) value;
    }

    public void setParseFile(ParseFile file) {
        put(KEY_PARSE_FILE, file);
    }
    public void setPassword(String password) {
        put(KEY_PASSWORD, password);
    }
    public void setEmail(String email) {
        put(KEY_EMAIL, email);
    }
    public String getZipcode(){ return getString(KEY_AREA_CODE);}
    public void setZipcode(String zipcode){ put(KEY_AREA_CODE, zipcode);}
    public String getCity(){return getString(KEY_CITY_NAME);}
    public void setCity(String city){put(KEY_CITY_NAME, city);}

}
