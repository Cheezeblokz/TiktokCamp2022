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
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

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
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, TodayFragment.class, null)
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
        etRegion = findViewById(R.id.etRegion);
        etDate = findViewById(R.id.etDate);
        tvResult = findViewById(R.id.tvResult);

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
                Intent i = new Intent(this, SettingActivity.class);
                startActivity(i);
                return true;
            case R.id.action_aboutPage:
                Intent i2 = new Intent(this, AboutPageActivity.class);
                startActivity(i2);
                return true;
            default:
                return true;
        }
    }

    //Comment: function for data extraction for reference
    EditText etRegion, etDate;
    TextView tvResult;
    private final String url = "https://api.data.gov.sg/v1/environment/pm25";

    public void getWeatherDetails(View view) {
        String tempUrl = "";
        String region = etRegion.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        if (region.equals("")) {
            tvResult.setText("Region field can not be empty!");
        } else {
            if (!date.equals("")) {
                tempUrl = url + "?q=" + region + "," + date;
            } else {
                tempUrl = url + "?q=" + region;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Log.d("response", response);
                    try {
                        JSONArray jsonArray = response.getJSONArray("items");


                            for (int i = 0;i < jsonArray.length(); i++) {
                                JSONObject airQuality = jsonArray.getJSONObject(i);

                                JSONObject jsonObjectReadings = airQuality.getJSONObject("readings");
                                JSONObject jsonObjectpm25 = jsonObjectReadings.getJSONObject("pm25_one_hourly");
                                String national = jsonObjectpm25.getString("National");
                                String north = jsonObjectpm25.getString("north");
                                String south = jsonObjectpm25.getString("south");
                                String east = jsonObjectpm25.getString("east");
                                String west = jsonObjectpm25.getString("west");
                                String central = jsonObjectpm25.getString("central");

                                mTextViewResult.append(national + ", " + "\n"
                                        + north + ", " + "\n"
                                        + south + ", " + "\n"
                                        + east + ", " + "\n"
                                        + west + ", " + "\n"
                                        + central + "\n");
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}