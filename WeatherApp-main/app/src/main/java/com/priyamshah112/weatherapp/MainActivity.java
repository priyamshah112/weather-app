package com.priyamshah112.weatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Menu menu;
    LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5,ll_6,ll_7;
    RecyclerView view_RecyclerData;
    SwipeRefreshLayout swiperLayout;
    TextView view_loc, view_CurrDateTime, view_Temp, view_TempFeelsLike,
            view_DescOfWeather, view_WindInfo, view_Humidity,
            view_UvIndexDayWeatherRow, view_Visibility, view_MorningTemp,
            view_NoonTemp, view_EveTemp, view_NightTemp,
            view_Sunrise,view_Sunset;
    ImageView imgIconForWeather;
    double lat=0.0;
    double lng=0.0;
    String loc=null;
    String jsonData;
    String default_locName="Chicago, Illinois";
    String default_lat="41.8675766";
    String default_lng="-87.616232";
    boolean isF=true;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        swiperLayout = findViewById(R.id.swiperLayout);
        ll_1  = findViewById(R.id.ll_1);
        ll_2  = findViewById(R.id.ll_2);
        ll_3 = findViewById(R.id.ll_3);
        ll_4 = findViewById(R.id.ll_4);
        ll_5 = findViewById(R.id.ll_5);
        ll_6 = findViewById(R.id.ll_6);
        ll_7 = findViewById(R.id.ll_7);
        view_loc  = findViewById(R.id.view_loc);
        view_CurrDateTime = findViewById(R.id.view_CurrentDateTime);
        view_Temp = findViewById(R.id.view_Temperature);
        view_WindInfo  = findViewById(R.id.view_WindInfo);
        view_Humidity  = findViewById(R.id.view_Humidity);
        view_MorningTemp = findViewById(R.id.view_MorningTemperature);
        view_NoonTemp = findViewById(R.id.view_AfternoonTemperature);
        view_EveTemp = findViewById(R.id.view_EveningTemperature);
        view_NightTemp = findViewById(R.id.view_NightTemperature);
        view_TempFeelsLike = findViewById(R.id.view_TemperatureFeelsLike);
        view_DescOfWeather = findViewById(R.id.view_DescriptionOfWeather);
        view_Sunrise  = findViewById(R.id.view_Sunrise);
        view_Sunset  = findViewById(R.id.view_Sunset);
        view_UvIndexDayWeatherRow  = findViewById(R.id.view_UVDayWeatherRow);
        view_Visibility  = findViewById(R.id.view_Visibility);
        imgIconForWeather  = findViewById(R.id.imgIconForWeather);
        view_RecyclerData  = findViewById(R.id.recyclerViewData);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ChangeVisibility();
        onClicks();
        loadData();
    }

    private void loadData() {
        view_loc.setText(prefs.getString("address", default_locName));
        lat = Double.parseDouble(prefs.getString("latitude", default_lat));
        lng = Double.parseDouble(prefs.getString("longitude", default_lng));
        loc = prefs.getString("address",default_locName);
        isF = prefs.getBoolean("degreeselected", true);
        getSupportActionBar().setTitle(loc);

        if (hasNetworkConnection()) {
            CrossingWeatherAPI crossingWeatherAPI = new CrossingWeatherAPI(MainActivity.this, isF, loc);
            new Thread(crossingWeatherAPI).start();
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_internet_msg, Toast.LENGTH_LONG).show();
        }
    }

    private void onClicks() {

        swiperLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(hasNetworkConnection()) {
                    CrossingWeatherAPI crossingWeatherAPI = new CrossingWeatherAPI(MainActivity.this, isF, loc);
                    new Thread(crossingWeatherAPI).start();
                    swiperLayout.setRefreshing(false);
                    ll_1.setVisibility(View.VISIBLE);
                    ll_2.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.no_internet_msg,Toast.LENGTH_LONG).show();
                    try{
                        ll_1.setVisibility(View.GONE);
//                        ll_3.setVisibility(View.GONE);
//                        ll_4.setVisibility(View.GONE);
//                        ll_5.setVisibility(View.GONE);
//                        ll_6.setVisibility(View.GONE);
//                        ll_7.setVisibility(View.GONE);
                        ll_2.setVisibility(View.VISIBLE);}
                    catch (Exception e){
                        ll_1.setVisibility(View.GONE);
                        ll_3.setVisibility(View.GONE);
                        ll_4.setVisibility(View.GONE);
                        ll_5.setVisibility(View.GONE);
                        ll_2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    //Network Connection
    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = null;
        connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    public void ChangeVisibility(){
        if(hasNetworkConnection()){
            ll_1.setVisibility(View.VISIBLE);
            ll_2.setVisibility(View.GONE);
        }
        else{
            try{
                ll_1.setVisibility(View.GONE);
                ll_3.setVisibility(View.GONE);
                ll_4.setVisibility(View.GONE);
                ll_5.setVisibility(View.GONE);
                ll_6.setVisibility(View.GONE);
                ll_7.setVisibility(View.GONE);
                ll_2.setVisibility(View.VISIBLE);}
            catch (Exception e){
                ll_1.setVisibility(View.GONE);
                ll_3.setVisibility(View.GONE);
                ll_4.setVisibility(View.GONE);
                ll_5.setVisibility(View.GONE);
                ll_2.setVisibility(View.VISIBLE);
            }
        }
    }

    private String getLName(String userProvidedloc) {
        String la = "";
        String lo = "";
        Geocoder gCoder = new Geocoder(this);
        try {
            List<Address> address =
                    gCoder.getFromLocationName(userProvidedloc, 1);
            if (address != null && address.size()!=0) {
                String country = address.get(0).getCountryCode();
                if (country.equals("US")) {
                    la = address.get(0).getLocality();
                    lo = address.get(0).getAdminArea();
                } else {
                    la = address.get(0).getLocality();
                    if (la == null)
                        la = address.get(0).getSubAdminArea();
                    lo = address.get(0).getCountryName();
                }
                loc = la + ", " + lo;
                return loc;
            }
            Toast.makeText(this,R.string.invalid_city_name,Toast.LENGTH_LONG).show();
            return null;
        }
        catch (IOException e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        MenuItem menuItem = menu.getItem(0);
        if(isF){
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.units_c));
        }
        else{
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.units_f));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        ChangeVisibility();
        if (menuItem.getItemId() == R.id.menuDayWiseForecast) {
            if(hasNetworkConnection()) {
                Intent intent = new Intent(this, DayWiseForecast.class);
                intent.putExtra("dailyData", jsonData);
                intent.putExtra("farenheit", isF);
                intent.putExtra("loc", view_loc.getText().toString());
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),R.string.no_internet_msg,Toast.LENGTH_LONG).show();
            }
            return true;

        }
        else if (menuItem.getItemId() == R.id.menuUnitTemp) {
            if(hasNetworkConnection()) {
                if (isF) {
                    isF = false;
                    menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.units_f));
                    CrossingWeatherAPI crossingWeatherAPI = new CrossingWeatherAPI(MainActivity.this, isF, loc);
                    new Thread(crossingWeatherAPI).start();
                } else {
                    isF = true;
                    menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.units_c));
                    CrossingWeatherAPI crossingWeatherAPI = new CrossingWeatherAPI(MainActivity.this, isF, loc);
                    new Thread(crossingWeatherAPI).start();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),R.string.no_internet_msg,Toast.LENGTH_LONG).show();
            }
            return true;
        }

        else if (menuItem.getItemId() == R.id.menulocChange) {
            if(hasNetworkConnection()) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                final EditText editText = new EditText(this);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setGravity(Gravity.CENTER_HORIZONTAL);
                alertBuilder.setView(editText);
                alertBuilder.setTitle(R.string.dialogue_title);
                alertBuilder.setMessage(R.string.dialogue_message);

                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean isSuccess = getLatLon(editText.getText().toString().trim());
                        if(isSuccess) {
                            view_loc.setText(getLName(editText.getText().toString().trim()));
                            prefs.edit().putString("address", view_loc.getText().toString()).apply();
                            prefs.edit().putString("latitude", "" + lat).apply();
                            prefs.edit().putString("longitude", "" + lng).apply();
                            prefs.edit().putBoolean("degreeselected", isF).apply();
                            getSupportActionBar().setTitle(loc);
                            CrossingWeatherAPI crossingWeatherAPI = new CrossingWeatherAPI(MainActivity.this, isF, loc);
                            new Thread(crossingWeatherAPI).start();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),R.string.invalid_city_name,Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
            }
            else{
                Toast.makeText(getApplicationContext(),R.string.no_internet_msg,Toast.LENGTH_LONG).show();
            }
            return true;
        }
        else {
            return super.onOptionsItemSelected(menuItem);
        }
    }
    private boolean getLatLon(String userProvidedloc) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> address =
                    geocoder.getFromLocationName(userProvidedloc, 1);
            if (address != null && address.size()!=0) {
                lat = address.get(0).getLatitude();
                lng = address.get(0).getLongitude();
                return true;
            }
        } catch (IOException e) {
            Log.e("Error", e.getMessage());
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateData(Weather w, String jData) {
        if (w == null) {
            Toast.makeText(this, R.string.invalid_city_name, Toast.LENGTH_SHORT).show();
            return;
        }
        this.jsonData = jData;
        view_Temp.setText(String.format("%.0f° " + (isF ? "F" : "C"), Double.parseDouble(w.getTemp())));
        view_Humidity.setText(String.format(Locale.getDefault(), "Humidity: %.0f%%", Double.parseDouble(w.getHumidity())));
        imgIconForWeather.setImageResource(getResources().getIdentifier(
                w.iconName, "drawable", "com.priyamshah112.weatherapp"));
        view_UvIndexDayWeatherRow.setText("UV Index : " + String.format("%s", w.getUvi()));
        view_TempFeelsLike.setText("Feels Like " + String.format("%.0f° " + (isF ? "F" : "C"), Double.parseDouble(w.getFeelslike())));
        view_NightTemp.setText(String.format("%.0f° " + (isF ? "F" : "C"), Double.parseDouble(w.getNightTemp())));
        view_Sunrise.setText("Sunrise : " + w.sunrise);
        view_Sunset.setText("Sunset : " + w.sunset);
        view_WindInfo.setText(String.format("Winds: " + wDirection(Double.parseDouble(w.getWinddegree())) + " at %.0f " + (isF ? "mph" : "mps"), Double.parseDouble(w.getWindspeed())) + " gusting to " + (w.getWindgust()==null ? "0.0" : w.getWindgust()) + (isF ? " mph" : " mps"));
        view_DescOfWeather.setText(String.format("%s", w.getDescription()));
        view_MorningTemp.setText(String.format("%.0f° " + (isF ? "F" : "C"), Double.parseDouble(w.getMorningTemp())));
        view_NoonTemp.setText(String.format("%.0f° " + (isF ? "F" : "C"), Double.parseDouble(w.getDayTemp())));
        view_EveTemp.setText(String.format("%.0f° " + (isF ? "F" : "C"), Double.parseDouble(w.getEveningTemp())));
        view_Visibility.setText("Visibility : " + String.format("%s", (Double.parseDouble(w.getVisibility())) + " miles"));
        view_CurrDateTime.setText(w.timeZone);

        //Set the hourly weather data to be displayed in the horizontal Recycler View
        view_RecyclerData.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        ArrayList<HourlyData> hW = new ArrayList<HourlyData>();
        try {
            JSONObject jObjMain = new JSONObject(jsonData);
            JSONArray daysList = jObjMain.getJSONArray("days");

            for (int i = 0; i < 3; i++) {
                JSONObject dateData = daysList.getJSONObject(i);
                JSONArray hourDataList = dateData.getJSONArray("hours");
                JSONObject hourData ;
                for (int j = 0; j < hourDataList.length(); j++) {
                    hourData = hourDataList.getJSONObject(j);
                    String description = hourData.getString("conditions");
                    String icon = hourData.getString("icon").replace("-", "_");
                    String temp = String.format("%.0f° " + (isF ? "F" : "C"), Double.parseDouble(hourData.getString("temp")));
                    String timeZoneOffset = jObjMain.getString("tzoffset");
                    String datetimeEpoch = hourData.getString("datetimeEpoch");

                    JSONObject current_D = jObjMain.getJSONObject("currentConditions");
                    String datetimeEpoch_1 = current_D.getString("datetimeEpoch");

                    Date dateTime = new Date(Long.parseLong(datetimeEpoch) * 1000);
                    Date current = new Date(Long.parseLong(datetimeEpoch_1) * 1000);

                    SimpleDateFormat dayDate = new SimpleDateFormat("EEEE", Locale.getDefault());
                    SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());

                    String day = dayDate.format(dateTime);
                    String today = dayDate.format(current);
                    String time = timeOnly.format(dateTime);

                    if (day.equalsIgnoreCase(today)) {
                        day = "Today";
                    }

                    HourlyData hData = new HourlyData(day, time, icon, temp, description, hourData.getString("datetimeEpoch"), timeZoneOffset);
                    hW.add(hData);

                    HourlyDataAdapter hourlyAdapter = new HourlyDataAdapter(hW, MainActivity.this);
                    view_RecyclerData.setAdapter(hourlyAdapter);
                }
            }
        } catch (Exception e) {
            Log.e("Error",e.getMessage());
        }
    }

    private String wDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X";
    }

}