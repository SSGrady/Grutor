package com.example.grutor.Utility;

import android.content.Context;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.grutor.Modals.User;
import com.example.grutor.R;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QueryUtils {

    public QueryUtils () {
    }

    public final String API_URL = "https://api.weatherbit.io/v2.0/current";
    public static final String POSTAL_CODE = "postal_code";
    public static final String API_KEY_PARAM = "key";

    public WeatherHour extractFeatureFromJson(JSONObject jsonObject) throws JSONException{

        WeatherHour currentForecast = new WeatherHour("", "", "", "", 0);
        // If the JSON string is empty or null, then return early.

        try {

            // Create a JSONObject from the JSON response string

            JSONArray weatherHourArray = jsonObject.getJSONArray("data");

            JSONObject currentHour = weatherHourArray.getJSONObject(0);

            JSONObject weatherCode = currentHour.getJSONObject("weather");

            String cityName = currentHour.getString("city_name");
            double feels_like = currentHour.getDouble("app_temp");
            String timezone = currentHour.getString("timezone");
            String icon_code = weatherCode.getString("icon");
            String description = weatherCode.getString("description");

           currentForecast.setWeatherIconCode(icon_code);
           currentForecast.setWeatherDescription(description);
           currentForecast.setTimeZone(timezone);
           currentForecast.setCityName(cityName);
           currentForecast.setApparentTemperature(feels_like);

            // Add the new instance to the list of Weatherhours.

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the hourly weather JSON results", e);
        }
        return currentForecast;
    }

    public void getHomeMessage(Context context, JsonHttpResponseHandler jsonHttpResponseHandler) {
        AsyncHttpClient mClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        User currentUser = (User) ParseUser.getCurrentUser();
        params.put(POSTAL_CODE, currentUser.getZipcode());
        params.put(API_KEY_PARAM, context.getString(R.string.weatherbit_master_api_key));
        mClient.get(API_URL, params, jsonHttpResponseHandler);
    }

}
