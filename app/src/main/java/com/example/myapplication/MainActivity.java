package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    //Comment: components shifted to SearchFragment
//    private TextView mTextViewResult;
//    private RequestQueue mQueue;
//    private Button buttonParse;

    SearchFragment sf = new SearchFragment();
    TodayFragment tf = new TodayFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Attaching SearchFragment to FragmentContainer layout
        //Hongyi: the following might make switching between fragments very tedious
        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, SearchFragment.class, null)
                .addToBackStack(null)
                .commit();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, TodayFragment.class, null)
                .addToBackStack(null)
                .commit();
         */

        //Intiates the two fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .add(R.id.fragment_container_view, sf)
                .addToBackStack(null)
                .add(R.id.fragment_container_view, tf)
                .addToBackStack(null)
                .show(sf)
                .hide(tf)
                .commit();

        //Comment: shifted to SearchFragment
//        mTextViewResult = findViewById(R.id.text_view_result);
//        buttonParse = findViewById(R.id.button_parse);
//        mQueue requestQueue = Volley.newRequestQueue(this);
//
//        buttonParse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jsonParse();
//            }
//        });

        // debug: Previously the following lines were comments, resulting in the top menu not being
        // displayed, so adjust the comment
        // Configuring Toolbar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.menu_main);

        BottomNavigationView myBottomNavigationView = findViewById(R.id.bottomNavigationView);
        //myBottomNavigationView.inflateMenu(R.menu.my_navigation_items); //don't inflate twice
        myBottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // feature: to enable menu icons display
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    // Handle selection action for menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                Intent intentToSetting = new Intent(this, SettingActivity.class);
                startActivity(intentToSetting);
                return true;
            case R.id.action_aboutPage:
                Intent intentToAboutPage = new Intent(this, AboutPageActivity.class);
                startActivity(intentToAboutPage);
                return true;
            default:
                return true;
        }
    }

    //Comment: function for data extraction for reference
    EditText etRegion, etDate;
    TextView tvResult;
    private static final String url = "https://api.data.gov.sg/v1/environment/pm25";

    // input: date-string(format yyyymmdd, only 8 digits input acceptable)
    // input: time(format HHmmss, only 6 digits 000000-235959hrs, otherwise ignored the time)
    // output/return: the URL for request, only if the input is legal, else return a "" string

    public static String UrlGenerator(String date, String time){
        String tempStr = "";
        tempStr += url;

        // check the length of the date
        if (date.length()!=8){
            return "";
        }

        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6,8);

        // check the format of the date
        if ( Integer.parseInt(month) > 12  || Integer.parseInt(month) < 1  ){
            return "";
        }
        if (1 == Integer.parseInt(month) || 3 == Integer.parseInt(month) ||
                5 == Integer.parseInt(month) || 7 == Integer.parseInt(month) ||
                8 == Integer.parseInt(month) || 10 == Integer.parseInt(month) ||
                12 == Integer.parseInt(month) ){
            if ( Integer.parseInt(day) > 31  || Integer.parseInt(day) < 1  ){
                return "";
            }
        }else if (4 == Integer.parseInt(month) || 6 == Integer.parseInt(month) ||
                9 == Integer.parseInt(month) || 11 == Integer.parseInt(month)){
            if ( Integer.parseInt(day) > 30  || Integer.parseInt(day) < 1  ){
                return "";
            }
        }
        else if (2 == Integer.parseInt(month)){
            if (Integer.parseInt(year) % 4 == 0){
                if ( Integer.parseInt(day) > 29  || Integer.parseInt(day) < 1  ){
                    return "";
                }
            } else {
                if ( Integer.parseInt(day) > 28  || Integer.parseInt(day) < 1  ){
                    return "";
                }
            }
        }


        String new_date = year + "-" + month + "-" + day;


        if (time.length() == 6){
            String hour = time.substring(0,2);
            String minute = time.substring(2,4);
            String second = time.substring(4,6);

            if (Integer.parseInt(hour) <= 23 && Integer.parseInt(hour) >= 0 &&
                    Integer.parseInt(minute) <= 59 && Integer.parseInt(minute) >= 0 &&
                    Integer.parseInt(second) <= 59 && Integer.parseInt(second) >= 0){
                String new_time = hour + "%3A" + minute+ "%3A" + second;
                // https://api.data.gov.sg/v1/environment/pm25?date_time=2022-03-27T11%3A22%3A33
                tempStr += "?date_time=" +new_date + "T" + new_time;
            } else {
                // directly ignore the time input if the length is illegal
                // https://api.data.gov.sg/v1/environment/pm25?date=2022-03-27
                tempStr += "?date=" + new_date;
            }
        } else{
            // directly ignore the time input if the time input is unformatted
            // https://api.data.gov.sg/v1/environment/pm25?date=2022-03-27
            tempStr += "?date=" + new_date;
        }

        return tempStr;
    }

    public static void getWeatherDetails(Context context, String region, String url, TextView tvResult) {
//        String tempUrl = "";
//        if (region.equals("")) {
//            tvResult.setText("Region field can not be empty!");
//        } else {
//            if (!date.equals("")) {
//                tempUrl = url + "?q=" + region + "," + date;
//            } else {
//                tempUrl = url + "?q=" + region;
//            }
        //Comment: resets the TextView
        tvResult.setText("");

        //Extract readings from url
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("items");

                            for (int i = 0;i < jsonArray.length(); i++) {
                                JSONObject airQuality = jsonArray.getJSONObject(i);

                                JSONObject jsonObjectReadings = airQuality.getJSONObject("readings");
                                JSONObject jsonObjectpm25 = jsonObjectReadings.getJSONObject("pm25_one_hourly");
                                String updateTimestamp = airQuality.getString("update_timestamp");
                                String north = jsonObjectpm25.getString("north");
                                String south = jsonObjectpm25.getString("south");
                                String east = jsonObjectpm25.getString("east");
                                String west = jsonObjectpm25.getString("west");
                                String central = jsonObjectpm25.getString("central");

                                tvResult.append("Update at " + updateTimestamp + "\n"
                                        + "North: " + north + "\n"
                                        + "South: " + south + "\n"
                                        + "East: " + east + "\n"
                                        + "West: " + west + "\n"
                                        + "Central: " + central + "\n\n");




                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
//        }
    }

    //Configure actions for selecting menu items in navigation bar
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case (R.id.item_search):
                fragmentManager.beginTransaction()
                        .hide(tf)
                        .show(sf)
                        .commit();
                break;
            case (R.id.item_today):
                fragmentManager.beginTransaction()
                        .hide(sf)
                        .show(tf)
                        .commit();
                break;
            default:
                break;
        }
        return true;
    }
}