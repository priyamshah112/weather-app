package com.priyamshah112.weatherapp;

public class DayWiseData {
    public String date, temp, description, precipitation, uvindex;
    public String morning, day, evening, night,iconcode;
    public boolean fahrenheit;


    public DayWiseData(String date, String temp, String description, String precipitation, String uvindex, String morning, String day, String evening, String night, String iconcode,boolean farenheit) {
        this.date = date;
        this.temp = temp;
        this.description = description;
        this.precipitation = precipitation;
        this.uvindex = uvindex;
        this.morning = morning;
        this.day = day;
        this.evening = evening;
        this.night = night;
        this.iconcode = iconcode;
        this.fahrenheit = farenheit;
    }
    public String getDay() {
        return day;
    }

    public String getMorning() {
        return morning;
    }

    public String getEvening() {
        return evening;
    }

    public String getNight() {
        return night;
    }
}
