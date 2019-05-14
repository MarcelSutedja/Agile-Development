package com.example.schoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;

public class TimeTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        PhotoView timeTable = (PhotoView) findViewById(R.id.photo_view);
        timeTable.setImageResource(R.drawable.timetable);
    }
}
