package com.example.myapplication;

import static com.example.myapplication.MainActivity.UrlGenerator;

import org.junit.Test;

import static org.junit.Assert.*;

import junit.framework.Assert;

public class UnitTestforURLGenerator {

    @Test
    public void UrlGeneratorCheck_correct_date_and_correct_time(){
        // should return the correct URL with date and time
        // arrange
        String date = "20220405";  //format: yyyymmdd
        String time = "213109";    //format: HHmmss
        String actual_result = "";
        String expect_result = "https://api.data.gov.sg/v1/environment/pm25?date_time=2022-04-05T21%3A31%3A09";

        // act
        actual_result = UrlGenerator(date, time);

        // assert
        assertEquals(expect_result, actual_result);
    }

    @Test
    public void UrlGeneratorCheck_ILLEGALMONTH_date_and_NULL_time(){

        // arrange
        String date = "20221305";  //format: yyyymmdd, illegal month
        String time = "";    //format: HHmmss
        String actual_result = "";
        String expect_result = "";

        // act
        actual_result = UrlGenerator(date, time);

        // assert
        assertEquals(expect_result, actual_result);
    }

    @Test
    public void UrlGeneratorCheck_ILLEGALDAY_date_and_NULL_time(){

        // arrange
        String date = "20221131";  //format: yyyymmdd, illegal month
        String time = "";    //format: HHmmss
        String actual_result = "";
        String expect_result = "";

        // act
        actual_result = UrlGenerator(date, time);

        // assert
        assertEquals(expect_result, actual_result);
    }

    @Test
    public void UrlGeneratorCheck_correct_date_and_NULL_time(){
        // should return the correct URL with date but ignoring time
        // arrange
        String date = "20220405";  //format: yyyymmdd
        String time = "";    //format: HHmmss
        String actual_result = "";
        String expect_result = "https://api.data.gov.sg/v1/environment/pm25?date=2022-04-05";

        // act
        actual_result = UrlGenerator(date, time);

        // assert
        assertEquals(expect_result, actual_result);
    }

    @Test
    public void UrlGeneratorCheck_correct_date_and_WRONGFORMAT_time(){
        // should return the correct URL with date but ignoring time
        // arrange
        String date = "20220405";  //format: yyyymmdd
        String time = "2156";    //format: HHmmss, test the wrong format
        String actual_result = "";
        String expect_result = "https://api.data.gov.sg/v1/environment/pm25?date=2022-04-05";

        // act
        actual_result = UrlGenerator(date, time);

        // assert
        assertEquals(expect_result, actual_result);
    }

    @Test
    public void UrlGeneratorCheck_correct_date_and_ILLEGALHOUR_time(){
        // should return the correct URL with date but ignoring time
        // arrange
        String date = "20220405";  //format: yyyymmdd
        String time = "250101";    //format: HHmmss
        String actual_result = "";
        String expect_result = "https://api.data.gov.sg/v1/environment/pm25?date=2022-04-05";

        // act
        actual_result = UrlGenerator(date, time);

        // assert
        assertEquals(expect_result, actual_result);
    }

    @Test
    public void UrlGeneratorCheck_correct_date_and_ILLEGALMINUTE_time(){
        // should return the correct URL with date but ignoring time
        // arrange
        String date = "20220405";  //format: yyyymmdd
        String time = "236101";    //format: HHmmss
        String actual_result = "";
        String expect_result = "https://api.data.gov.sg/v1/environment/pm25?date=2022-04-05";

        // act
        actual_result = UrlGenerator(date, time);

        // assert
        assertEquals(expect_result, actual_result);
    }

    @Test
    public void UrlGeneratorCheck_correct_date_and_ILLEGALSECOND_time(){
        // should return the correct URL with date but ignoring time
        // arrange
        String date = "20220405";  //format: yyyymmdd
        String time = "230161";    //format: HHmmss
        String actual_result = "";
        String expect_result = "https://api.data.gov.sg/v1/environment/pm25?date=2022-04-05";

        // act
        actual_result = UrlGenerator(date, time);

        // assert
        assertEquals(expect_result, actual_result);
    }
}
