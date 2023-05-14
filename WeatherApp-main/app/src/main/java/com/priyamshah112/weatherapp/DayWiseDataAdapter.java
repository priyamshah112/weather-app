package com.priyamshah112.weatherapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DayWiseDataAdapter extends RecyclerView.Adapter<DayWiseViewHolder> {
    public final List<DayWiseData> ldayWiseData;
    public final DayWiseForecast mainActivity;

    public DayWiseDataAdapter(ArrayList<DayWiseData> ldayWiseData, DayWiseForecast ma) {
            mainActivity = ma;
            this.ldayWiseData =ldayWiseData;
    }

    @NonNull
    @Override
    public DayWiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_weather_row, parent, false);

        return new DayWiseViewHolder(itemView);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DayWiseViewHolder dayWiseViewHolder, int position) {
        DayWiseData dayWiseData = ldayWiseData.get(position);

        dayWiseViewHolder.view_Date.setText(dayWiseData.date);
        dayWiseViewHolder.view_MinMax.setText(dayWiseData.temp);
        dayWiseViewHolder.view_UVIndex.setText("UV Index : "+dayWiseData.uvindex);
        dayWiseViewHolder.view_Description.setText(dayWiseData.description);
        dayWiseViewHolder.view_Precipitation.setText("( "+dayWiseData.precipitation+" )");
        dayWiseViewHolder.view_Morning.setText(String.format("%.0f° " + (dayWiseData.fahrenheit ? "F" : "C"), Double.parseDouble(dayWiseData.getMorning())));
        dayWiseViewHolder.view_Afternoon.setText(String.format("%.0f° " + (dayWiseData.fahrenheit ? "F" : "C"), Double.parseDouble(dayWiseData.getDay())));
        dayWiseViewHolder.view_Evening.setText(String.format("%.0f° " + (dayWiseData.fahrenheit ? "F" : "C"), Double.parseDouble(dayWiseData.getEvening())));
        dayWiseViewHolder.view_Night.setText(String.format("%.0f° " + (dayWiseData.fahrenheit ? "F" : "C"), Double.parseDouble(dayWiseData.getNight())));

        dayWiseViewHolder.view_Date.setText(dayWiseData.date);
        dayWiseViewHolder.imgWeatherIcon.setImageResource(mainActivity.getResources().getIdentifier(dayWiseData.iconcode, "drawable", "com.priyamshah112.weatherapp"));
    }

    @Override
    public int getItemCount() {
        return ldayWiseData.size();
    }
}