package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    private TextView mTextViewResult;
    private Button buttonParse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, null);
        mTextViewResult = v.findViewById(R.id.text_view_result);
        buttonParse = v.findViewById(R.id.button_parse);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getWeatherDetails(getContext(), "region", MainActivity.UrlGenerator("20220101",""), mTextViewResult);
            }
        });
        return v;
    }
}
