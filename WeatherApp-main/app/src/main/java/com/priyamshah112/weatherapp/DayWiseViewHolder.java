package com.priyamshah112.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DayWiseViewHolder extends RecyclerView.ViewHolder {

    TextView view_Date,view_MinMax,view_UVIndex,view_Description,view_Precipitation,view_Morning,view_Afternoon,view_Evening,view_Night;
    ImageView imgWeatherIcon;

    public DayWiseViewHolder(@NonNull View itemView) {
        super(itemView);
        view_Date = itemView.findViewById(R.id.view_DateDayWeatherRow);
        view_MinMax = itemView.findViewById(R.id.view_MinMaxTempDayWeatherRow);
        view_Evening = itemView.findViewById(R.id.view_EveningTempDayWeatherRow);
        view_Night = itemView.findViewById(R.id.view_NightTempDayWeatherRow);
        imgWeatherIcon = itemView.findViewById(R.id.view_WeatherIconDayWeatherRow);
        view_UVIndex = itemView.findViewById(R.id.view_UVIndexDayWeatherRow);
        view_Description = itemView.findViewById(R.id.view_DescriptionDayWeatherRow);
        view_Precipitation = itemView.findViewById(R.id.view_PrecipitationDayWeatherRow);
        view_Morning = itemView.findViewById(R.id.view_MorningTempDayWeatherRow);
        view_Afternoon = itemView.findViewById(R.id.view_AfternoonTempDayWeatherRow);
    }
}
