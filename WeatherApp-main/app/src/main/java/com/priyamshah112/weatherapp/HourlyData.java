package com.priyamshah112.weatherapp;

public class HourlyData {
    public String day;
    public String time;
    public String description;
    public String timestamp;
    public String icon;
    public String temp;
    public String timezoneOffset;

    public HourlyData(String day, String time, String icon, String temp, String description,String timestamp, String timezoneOffset) {
        this.day = day;
        this.description = description;
        this.timestamp = timestamp;
        this.time = time;
        this.icon = icon;
        this.temp = temp;
        this.timezoneOffset = timezoneOffset;
    }
}
