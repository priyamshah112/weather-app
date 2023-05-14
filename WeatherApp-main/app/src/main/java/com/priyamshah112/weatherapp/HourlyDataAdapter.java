package com.priyamshah112.weatherapp;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HourlyDataAdapter extends RecyclerView.Adapter<HourlyDataViewHolder> {
    public final List<HourlyData> hourlyData;
    public final MainActivity mainActivity;

    public HourlyDataAdapter(List<HourlyData> hourlyData, MainActivity Activitym) {
        this.hourlyData = hourlyData;
        mainActivity = Activitym;

    }

    @NonNull
    @Override
    public HourlyDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_weather_row_main, parent, false);
        return new HourlyDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyDataViewHolder holder, int position) {
        HourlyData hourlyWeather = hourlyData.get(position);
        holder.view_Day.setText(hourlyWeather.day);
        holder.view_Time.setText(hourlyWeather.time);
        holder.view_Temperature.setText(hourlyWeather.temp);
        holder.view_Description.setText(hourlyWeather.description);

        holder.imgWeather.setImageResource(mainActivity.getResources().getIdentifier(hourlyWeather.icon, "drawable", "com.priyamshah112.weatherapp"));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, Long.parseLong(hourlyWeather.timestamp)*1000+Long.parseLong(hourlyWeather.timezoneOffset));
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                mainActivity.startActivity(intent);
                Log.d("TimeIssue", "onClick: "+getDate(Long.parseLong(hourlyWeather.timestamp)*1000+Long.parseLong(hourlyWeather.timezoneOffset), "dd/MM/yyyy hh:mm:ss.SSS"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return hourlyData.size();
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateFormat);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(milliSeconds);
        return dateFormat1.format(calendar1.getTime());
    }
}
