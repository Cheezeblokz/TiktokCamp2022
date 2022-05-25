package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchFragment extends Fragment {

    //Spinner region;
    //TextView result;
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private Button buttonParse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, null);
        mTextViewResult = v.findViewById(R.id.text_view_result);
        buttonParse = v.findViewById(R.id.button_parse);
        mQueue = Volley.newRequestQueue(this.getContext());

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });

        return v;
    }

    private void jsonParse() {
        String url = "https://api.data.gov.sg/v1/environment/pm25";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");

                            for (int i = 0;i < jsonArray.length(); i++){
                                JSONObject airQuality = jsonArray.getJSONObject(i);

                                String updatedTime = airQuality.getString("update_timestamp");
                                String time = airQuality.getString("timestamp");
                                //insert one more json object for readings
                                /*JSONObject readings = airQuality.getJSONObject("pm25_one_hourly");
                                int nationalPM = readings.getInt("national");*/

                                mTextViewResult.append(updatedTime + ", " + time + "\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
