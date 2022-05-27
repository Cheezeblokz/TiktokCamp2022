package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getWeatherDetails(getContext(), "region", dateTime.getText().toString(), mTextViewResult);
            }
        });
        return v;
    }
}
