package com.example.grutor.Utility;

import java.sql.Time;
import java.util.TimeZone;

public class WeatherHour {

    private String mWeatherIconCode;
    private String mWeatherDescription;
    private String mTimeZone;
    private double mTemperature;
    private double mApparentTemperature;

    public WeatherHour(String weather_icon_code, String description, String timezone,
                       double temperature, double feelslike) {
        mWeatherIconCode = weather_icon_code;
        mTemperature = temperature;
        mTimeZone = timezone;
        mWeatherDescription = description;
        mApparentTemperature = feelslike;
    }

    public String getWeatherIconCode() {
        return mWeatherIconCode;
    }
    public void setWeatherIconCode(String weatherIconCode) { mWeatherIconCode = weatherIconCode;}

    public String getWeatherDescription() {
        return mWeatherDescription;
    }
    public void setWeatherDescription(String weatherDescription) { mWeatherDescription = weatherDescription;}

    public double getTemperature() {
        return mTemperature;
    }
    public void setTemperature(Double temperature) { mTemperature = temperature;}

    public String getTimeZoneString() {
        return mTimeZone;
    }
    public void setTimeZone(String timeZone) { mTimeZone = timeZone;}

    public double getApparentTemperature() {
        return mApparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) { mApparentTemperature = apparentTemperature;}
}