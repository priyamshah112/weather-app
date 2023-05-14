package com.priyamshah112.weatherapp;

import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DayWiseForecast extends AppCompatActivity {
    TextView editloc;
    RecyclerView recyclerDayView;
    ArrayList<DayWiseData> dayWiseData = new ArrayList<DayWiseData>();
    String jData;
    boolean fahrenheit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_wise_forecast);
        editloc = findViewById(R.id.txtloc);
        recyclerDayView = findViewById(R.id.recyclerViewDayWiseForecast);
        loadData();
    }

    @SuppressLint("SetTextI18n")
    public void loadData(){
        jData = getIntent().getStringExtra("dailyData");
        fahrenheit = getIntent().getBooleanExtra("fahrenheit",true);
        editloc.setText(""+getIntent().getStringExtra("loc")+" 15 Day");

        try {
            JSONObject jObjMain = new JSONObject(jData);
            JSONArray daily = jObjMain.getJSONArray("days");
            for(int i = 0 ; i<daily.length();i++){
                JSONObject dateObject = daily.getJSONObject(i);
                String iconCode = (dateObject.getJSONArray("hours").getJSONObject(0).getString("icon")).replace("-","_");
                String description = (dateObject.getString("description"));
                String precipitation = dateObject.getString("precipprob") + "% precip.";
                String uvi = dateObject.getString("uvindex");

                JSONObject jDaily = (JSONObject) daily.get(0);
                JSONArray jDaily1 = jDaily.getJSONArray("hours");
                JSONObject jDaily_mor = (JSONObject)jDaily1.get(8);
                JSONObject jDaily_afternoon = (JSONObject)jDaily1.get(13);
                JSONObject jDaily_evening = (JSONObject)jDaily1.get(17);
                JSONObject jDaily_night = (JSONObject)jDaily1.get(23);

                String afternoon = jDaily_afternoon.getString("temp");
                String evening = jDaily_evening.getString("temp");
                String morning = jDaily_mor.getString("temp");
                String night = jDaily_night.getString("temp");

                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(Long.parseLong(dateObject.getString("datetimeEpoch")) * 1000);
                @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("EEEE, MM/dd").format(cal.getTime());

                String maxMinTemperature = String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(dateObject.getString("tempmax"))) +" / " +String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(dateObject.getString("tempmin")));

                DayWiseData dayWise = new DayWiseData(date, maxMinTemperature, description, precipitation, uvi, morning, afternoon, evening, night, iconCode, fahrenheit);
                dayWiseData.add(dayWise);
            }
            DayWiseDataAdapter dailyAdapter = new DayWiseDataAdapter(dayWiseData,DayWiseForecast.this);
            recyclerDayView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerDayView.setAdapter(dailyAdapter);

        } catch (Exception e) {
            Log.d("Error",""+e.getMessage());
        }
    }
}
