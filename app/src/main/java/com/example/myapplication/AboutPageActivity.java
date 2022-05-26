package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        Element project_detail = new Element();
        project_detail.setTitle("This is the project detail. TBD...");

        View about_page = new AboutPage(this)
                //.isRTL(false)
                //.setCustomFont(String) // or Typeface
                .setDescription("An Air Quality Index APP\nV1.0 May 2022\n\nGroup Project @TikTokYouthCamp2022")

                .addGroup("About this project")
                .addItem(project_detail)
                .addItem(project_detail)
                .addItem(project_detail)

                .addGitHub("https://github.com/Cheezeblokz/TiktokCamp2022")
                .addGroup("")

                .addGroup("Our group Members")
                // members' names and emails
                .addEmail("123@123.com", "Jingxi")
                .addEmail("234@234.com", "Hongyi")
                .addEmail("123@123.com", "Yee Sen")
                .addEmail("234@234.com", "Hong Yuan")
                .addEmail("123@123.com", "Jiahan")
                .addGroup("")

                .create();
        setContentView(about_page);

    }
}