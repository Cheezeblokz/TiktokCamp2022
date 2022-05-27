package com.example.myapplication;

import android.os.Bundle;
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
    private EditText dateTime;
    private String date, time, region;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, null);
        mTextViewResult = v.findViewById(R.id.text_view_result);
        buttonParse = v.findViewById(R.id.button_parse);
        dateTime = (EditText) v.findViewById(R.id.etDate);

        //Configuring spinner and storing selection by user into String region
        region = "All";
        String[] regions = {"All", "North", "South", "East", "West", "Central"};
        Spinner spinner = v.findViewById(R.id.region);
        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.simple_spinner_item, regions);
        aa.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = regions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Configure button to show readings onClick
        //Filter unrecognised date and time
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = dateTime.getText().toString();
                if(temp.length() == 19 &&
                temp.charAt(4) == '/' &&
                temp.charAt(7) == '/' &&
                temp.charAt(10) == ' ' &&
                temp.charAt(13) == ':' &&
                temp.charAt(16) == ':'){
                    date = temp.substring(0, 4) + temp.substring(5, 7) + temp.substring(8, 10);
                    time = temp.substring(11, 13) + temp.substring(14, 16) + temp.substring(17, 19);
                    if (MainActivity.UrlGenerator(date, time) != "") {
                        MainActivity.getWeatherDetails(getContext(), region, MainActivity.UrlGenerator(date, time), mTextViewResult);
                    }else{
                        mTextViewResult.setText("Date & Time format not recognized!");
                    }
                }else if(temp.length() == 0){
                    mTextViewResult.setText("Please input Date & Time!");
                }else{
                    mTextViewResult.setText("Date & Time format not recognized!");
                }
            }
        });
        return v;
    }
}
