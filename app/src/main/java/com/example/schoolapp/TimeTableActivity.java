package com.example.schoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.example.schoolapp.Extra.GlobalVar;
import com.github.chrisbanes.photoview.PhotoView;

public class TimeTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        PhotoView timeTable = (PhotoView) findViewById(R.id.photo_view);
        if (GlobalVar.getData()!=null) {
            switch (GlobalVar.getData()) {
                case ("Bachelor of Arts with Honours in Accounting and Finance"): {
                    timeTable.setImageResource(R.drawable.acctandfinanc);
                }
                case ("Bachelor of Arts with Honours in Business and Marketing"): {
                    timeTable.setImageResource(R.drawable.bznmarket);
                }
                case ("Bachelor of Engineering with Honours in Mechanical Engineering"): {
                    timeTable.setImageResource(R.drawable.mecheng);
                }
                case ("Bachelor of Science with Honours in Global Logistics (Top-up)"): {
                    timeTable.setImageResource(R.drawable.globallogs);
                }
                default:
            }
        }else{
            Toast haveNotSetTimeTable = Toast.makeText(TimeTableActivity.this, "Please go to the Profile Page and set your Time Table First", Toast.LENGTH_LONG);
            haveNotSetTimeTable.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 1250);
            haveNotSetTimeTable.show();
        }


    }
}
