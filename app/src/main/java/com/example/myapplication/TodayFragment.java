package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment {

    CompletableFuture<Void> ticking;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "date";
    private static final String ARG_PARAM2 = "time";
    private static final String ARG_PARAM3 = "pm25";

    // TODO: Rename and change types of parameters
    private String date;
    private String time;
    private String pm25;

    public TodayFragment() {
        // Required empty public constructor
        super(R.layout.fragment_today);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param date The current date.
     * @param time The current time.
     * @param pm25 The current reading of pm 2.5
     * @return A new instance of fragment TodayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayFragment newInstance(String date, String time, String pm25) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, date);
        args.putString(ARG_PARAM2, time);
        args.putString(ARG_PARAM3, pm25);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = "Null Date";
        time = "Null Time";
        pm25 = "Null pm 2.5";
        if (getArguments() != null) {
            date = getArguments().getString(ARG_PARAM1);
            time = getArguments().getString(ARG_PARAM2);
            pm25 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    //Sets the ticking when this fragment gets focus
    @Override
    public void onResume() {
        super.onResume();
        TextView txtTitle = (TextView) getView().findViewById(R.id.txtTitle);
        TextView txtDate = (TextView) getView().findViewById(R.id.txtDate);
        TextView txtTime = (TextView) getView().findViewById(R.id.txtTime);
        TextView txtPM25 = (TextView) getView().findViewById(R.id.txtPM25);

        txtTime.setText("Today's readings ");
        txtDate.setText(LocalDate.now().toString());

        //Updates the current local time every second concurrently
        ticking = CompletableFuture.runAsync(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage() + " - TodayFragment.java");
                }
                String timeString = "Current Time: " + LocalTime.now().toString().substring(0, 8);
                txtTime.setText(timeString);
            }
        }).handle((Void, ex) -> {
            System.out.println("Failed CompletableFuture - TodayFragment.java: " + ex.getMessage());
            return Void;
        });

        String date = LocalDate.now().toString();
        date = date.substring(0,4) + date.substring(5,7) + date.substring(8, 10);
        String time = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
        time = time.substring(0,2) + time.substring(3,5) + time.substring(6, 8);
        String targetURL = MainActivity.UrlGenerator(date, time);


        txtPM25.setText("PM2.5 Reading Now: ");
    }

    //Stops the ticking to save memory when this fragment loses focus
    @Override
    public void onPause() {
        super.onPause();
        ticking.complete(null);
        ticking = null;
    }

    /*
    editText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence c, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence c, int i, int i2, int i3) {
            if (c.length() > 0) {
            } else {
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    });

     */
}