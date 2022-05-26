package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener{

    Spinner region;
    TextView result;

    //Comment: components shifted to SearchFragment
//    private TextView mTextViewResult;
//    private RequestQueue mQueue;
//    private Button buttonParse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Attaching SearchFragment to FragmentContainer layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, SearchFragment.class, null)
                .addToBackStack(null)
                .commit();
        //Comment: shifted to SearchFragment
//        mTextViewResult = findViewById(R.id.text_view_result);
//        buttonParse = findViewById(R.id.button_parse);
//        mQueue = Volley.newRequestQueue(this);
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
        //myBottomNavigationView.inflateMenu(R.menu.my_navigation_items); don't inflate twice
        myBottomNavigationView.setOnItemSelectedListener(this);
    }

//    private void jsonParse() {
//        String url = "https://api.data.gov.sg/v1/environment/pm25";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("items");
//
//
//                            for (int i = 0;i < jsonArray.length(); i++){
//                                JSONObject airQuality = jsonArray.getJSONObject(i);
//
//                                String updatedTime = airQuality.getString("update_timestamp");
//                                String time = airQuality.getString("timestamp");
//                                //insert one more json object for readings
//                                /*JSONObject readings = airQuality.getJSONObject("pm25_one_hourly");
//                                int nationalPM = readings.getInt("national");*/
//
//                                mTextViewResult.append(updatedTime + ", " + time + "\n");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        mQueue.add(request);
//    }

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
    /*public void getWeatherDetails(View view) {
        String tempUrl = "";
//        String regionString = region.getText().toString().trim();

//        tempUrl = url + "?q=" + region;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Log.d("response", response);
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("items");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String timestamp = jsonObjectWeather.getString("timestamp");
                    output += "Current time : " + timestamp;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }*/

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case (R.id.item_search):
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container_view, SearchFragment.class, null)
                        .addToBackStack(null)
                        .commit();
                break;
            case (R.id.item_today):
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container_view, TodayFragment.class, null)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;
        }
        return true;
    }
}