package com.priyamshah112.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyDataViewHolder extends RecyclerView.ViewHolder {
    TextView view_Day,view_Time,view_Temperature,view_Description;
    ImageView imgWeather;
    LinearLayout linearLayout;

    public HourlyDataViewHolder(@NonNull View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.linearLayout);
        view_Day = itemView.findViewById(R.id.view_Day);
        view_Time = itemView.findViewById(R.id.view_Time);
        imgWeather = itemView.findViewById(R.id.imgWeather);
        view_Temperature = itemView.findViewById(R.id.view_Temperature);
        view_Description = itemView.findViewById(R.id.view_DescriptionDayWeatherRow);

    }
}
