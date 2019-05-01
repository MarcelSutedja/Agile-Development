package com.assignment.mrlau.adapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Gpa extends AppCompatActivity {

    public TextView result;
    public Button buttonCalc;
    double totalGPA=0;
    double totalCredit=0;

    public TextView textView;
    public TextView textView2;
    public TextView textView3;
    public TextView textView4;

    //variables
    public EditText examGrade;
    public EditText cwGrade;
    public EditText examPercentage;
    public EditText creditModule;

    private static final String LOG_TAG = "Gpa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);

        //variables
        result = (TextView) findViewById(R.id.result);
        buttonCalc = (Button) findViewById(R.id.buttonCalc);

        //arrays of editText(s)
        final int[] creditList = new int[] {R.id.editTextModuleCredit1,R.id.editTextModuleCredit2};
        final int[] gradeList = new int[] {R.id.editTextGrade1,R.id.editTextGrade2};
        final int[] cwgradeList = new int[] {R.id.editTextCourseWorkGrade1,R.id.editTextCourseWorkGrade2};
        final int[] percentageList = new int[] {R.id.editTextPercentage1,R.id.editTextPercentage2};

        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clearing the data
                totalGPA =0;
                totalCredit =0;
                for (int i=0; i<creditList.length;i++) {
                    //declaring the variables
                    examGrade = (EditText) findViewById(gradeList[i]);
                    double grade = Double.parseDouble(examGrade.getText().toString());
                    cwGrade = (EditText) findViewById(cwgradeList[i]);
                    double cwgrade = Double.parseDouble(cwGrade.getText().toString());
                    examPercentage = (EditText) findViewById(percentageList[i]);
                    double exampercentage = Double.parseDouble(examPercentage.getText().toString());
                    creditModule = (EditText) findViewById(creditList[i]);
                    double credit = Double.parseDouble(creditModule.getText().toString());

                    double examPercentage = exampercentage;
                    double courseworkPercentage = 100.0 - examPercentage;
                    double tempResult = (((grade * examPercentage) / 100.0) + (cwgrade * courseworkPercentage / 100.0)) / 100 * 4 * credit / 10;
                    totalGPA += tempResult;
                    totalCredit += credit / 10.0;
                }

                double resultFinal = totalGPA/totalCredit;
                result.setText(String.valueOf(resultFinal));
                if (resultFinal>=3.5){
                    result.setText("A : First Class Degree (" + String.valueOf(resultFinal) + ")");
                }
                else if (resultFinal<3.5 && resultFinal>=3){
                    result.setText("B : Second Upper Class Degree (" + String.valueOf(resultFinal)+ ")");
                }
                else if (resultFinal<3 && resultFinal>=2.5){
                    result.setText("B- : Second Lower Class Degree (" + String.valueOf(resultFinal)+ ")");
                }
                else if (resultFinal<2.5 && resultFinal>=2.0){
                    result.setText("General Degree (" + String.valueOf(resultFinal) + ")");
                }
                else{
                    result.setText("Fail : Remodule (" + String.valueOf(resultFinal) + ")");
                }
            }
        });
    }
}
