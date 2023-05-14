package com.priyamshah112.weatherapp;

import android.net.Uri;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CrossingWeatherAPI implements Runnable{

    private static final String TAG = "CrossingWeatherAPI";
    private static final String weather_url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final String weather_api = "2H2LPW339JPPJNAXCPTQZ97QF";
    private final boolean f;
    private final MainActivity Activitym;
    private static String loc;

    public CrossingWeatherAPI(MainActivity Activitym, boolean fahrenheit, String loc){
        this.Activitym = Activitym;
        this.f =fahrenheit;
        this.loc = loc;
    }
    @Override
    public void run() {
        Uri.Builder buildURL = Uri.parse(weather_url).buildUpon();
        buildURL.appendPath(String.valueOf(loc));
        buildURL.appendQueryParameter("unitGroup", (f ? "us" : "metric"));
        buildURL.appendQueryParameter("lang","en");
        buildURL.appendQueryParameter("key", weather_api);
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONObject> listener =
                response -> parseJSON(response.toString());

        Response.ErrorListener error =
                error1 -> Activitym.updateData(null,null);

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error);
        RequestQueue queue = Volley.newRequestQueue(Activitym);
        queue.add(jsonObjectRequest);
    }

    private void parseJSON(String str) {
        try {
            JSONObject jObjMain = new JSONObject(str);

            String timeZone = jObjMain.getString("timezone");
            String timeZoneOffset = jObjMain.getString("tzoffset");
            JSONObject jMain = jObjMain.getJSONObject("currentConditions");
            String datetimeEpoch = jMain.getString("datetimeEpoch");

            String humidity = jMain.getString("humidity");
            String feelsLike = jMain.getString("feelslike");
            String visibility = jMain.getString("visibility");
            String uvi = jMain.getString("uvindex");
            String sunrise = jMain.getString("sunriseEpoch");
            String sunset = jMain.getString("sunsetEpoch");
            String windspeed = jMain.getString("windspeed");
            String winddegree = jMain.getString("winddir");
            String conditions = jMain.getString("conditions");
            String clouds = jMain.getString("cloudcover");
            String description= conditions + String.format(" (%s%% clouds)",clouds);

            String icon = jMain.getString("icon").replace("-","_");
            String windgust=null;

            if(!jMain.isNull("windgust")){
                windgust = jMain.getString("windgust");
            }
            String temp = jMain.getString("temp");

            Date dt = new Date(Long.parseLong(datetimeEpoch)*1000);
            Date sun_rise = new Date(Long.parseLong(sunrise)*1000);
            Date sun_set = new Date(Long.parseLong(sunset)*1000);

            SimpleDateFormat formatted = new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
            SimpleDateFormat timeonly =  new SimpleDateFormat("h:mm a", Locale.getDefault());
            SimpleDateFormat dayDate = new SimpleDateFormat("EEEE MM/dd", Locale.getDefault());

            String formattedTimeString = formatted.format(dt);

            sunrise = timeonly.format(sun_rise);
            sunset = timeonly.format(sun_set);

            JSONArray daily = jObjMain.getJSONArray("days");

            JSONObject jDaily = (JSONObject) daily.get(0);
            JSONArray jDaily1 = jDaily.getJSONArray("hours");
            JSONObject jDaily_mor = (JSONObject)jDaily1.get(8);
            JSONObject jDaily_afternoon = (JSONObject)jDaily1.get(13);
            JSONObject jDaily_evening = (JSONObject)jDaily1.get(17);
            JSONObject jDaily_night = (JSONObject)jDaily1.get(23);

            String afternoon = jDaily_afternoon.getString("temp");
            String evening = jDaily_evening.getString("temp");
            String day = jDaily_mor.getString("temp");
            String night = jDaily_night.getString("temp");

            Weather weather;

            if(windgust == null){
                weather = new Weather("", "", description, temp, humidity, windspeed, winddegree,
                        "","",visibility, feelsLike, uvi,  day, afternoon, sunrise, sunset, evening,
                        night, formattedTimeString, icon);
            }
            else{
                weather = new Weather("", "", description, temp, humidity, windspeed, winddegree,
                        windgust, clouds, visibility, feelsLike, uvi, day, afternoon, sunrise, sunset, evening,
                        night, formattedTimeString, icon);
            }
            Activitym.runOnUiThread(() -> Activitym.updateData(weather,str));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "error: ", e);
        }

    }
}
