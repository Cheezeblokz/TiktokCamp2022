package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    private TextView mTextViewResult;
    private Button buttonParse;
    EditText dateTime;
    //TextView tvResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, null);
        mTextViewResult = v.findViewById(R.id.text_view_result);
        buttonParse = v.findViewById(R.id.button_parse);
        dateTime = (EditText) v.findViewById(R.id.etDate);

        //Configuring spinner and storing selection by user into String region
        String region = "north";
        String[] regions = {"North", "South", "East", "West", "Central"};
        Spinner spinner = v.findViewById(R.id.region);
        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.simple_spinner_item, regions);
        aa.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String region = regions[position].toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Configure button to show readings onClick
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getWeatherDetails(getContext(), region, MainActivity.UrlGenerator("20220101",""), mTextViewResult);
            }
        });
        return v;
    }
}
