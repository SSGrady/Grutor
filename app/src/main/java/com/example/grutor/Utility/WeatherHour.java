package com.example.grutor.Utility;

import java.sql.Time;
import java.util.TimeZone;

public class WeatherHour {

    private String mWeatherIconCode;
    private String mWeatherDescription;
    private String mTimeZone;
    private String mCityName;
    private double mApparentTemperature;

    public WeatherHour(String weather_icon_code, String description, String timezone,
                       String cityName, double feelslike) {
        mWeatherIconCode = weather_icon_code;
        mCityName = cityName;
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

    public String getCityName() {
        return mCityName;
    }
    public void setCityName(String cityName) { mCityName = cityName;}

    public String getTimeZoneString() {
        return mTimeZone;
    }
    public void setTimeZone(String timeZone) { mTimeZone = timeZone;}

    public double getApparentTemperature() {
        return mApparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) { mApparentTemperature = apparentTemperature;}
}