package com.example.schoolapp.Extra;

import android.widget.Toast;

public class GlobalVar {
    public static String TimeTable;
    public static String Major;

    public static String getData() {
        return TimeTable;
    }

    public static void setData(String timeTable) {
        TimeTable = timeTable;
    }

    public static String getMajor() {
        return Major;
    }

    public static void setMajor(String major) {
        Major = major;
    }
}
