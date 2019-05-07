package com.assignment.mrlau.adapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Gpa extends AppCompatActivity {
    public TextView result;
    public TextView module;

    public Button buttonCalc;
    public Button buttonSave;

    double totalMark=0;
    double totalCredit=0;

    public EditText examGrade;
    public EditText cwGrade;
    public EditText examPercentage;
    public EditText creditModule;

    int counter =1;

    private static final String LOG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);

        //textViews
        result = (TextView) findViewById(R.id.result);
        module = (TextView) findViewById(R.id.textViewModule1);

        //buttons
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonCalc = (Button) findViewById(R.id.buttonCalc);
        //arrays of editText(s)

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                examGrade = (EditText) findViewById(R.id.editTextGrade1);
                String sExamGrade = examGrade.getText().toString();
                cwGrade = (EditText) findViewById(R.id.editTextCourseWorkGrade1);
                String sCwGrade = cwGrade.getText().toString();
                examPercentage = (EditText) findViewById(R.id.editTextPercentage1);
                String sExamPercentage = examPercentage.getText().toString();
                creditModule = (EditText) findViewById(R.id.editTextModuleCredit1);
                String sCreditModule = creditModule.getText().toString();
                if (sExamGrade.matches("") || sCwGrade.matches("") || sExamPercentage.matches("") || sCreditModule.matches("")) {
                    AlertDialog.Builder x = new AlertDialog.Builder(Gpa.this);

                    x.setTitle("MISSING VALUE");
                    x.setMessage("No EMPTY box is allowed\nPlease check the inputs ^-^");
                    x.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = x.create();
                    alert.show();
                }
                else {
                    double dExamGrade=Double.parseDouble(sExamGrade);
                    double dCwGrade=Double.parseDouble(sCwGrade);
                    double dExamPercentage=Double.parseDouble(sExamPercentage);
                    double dCreditModule = Double.parseDouble(sCreditModule);
                    double tempMark = ((dExamGrade*dExamPercentage/100.0) + (dCwGrade*(100-dExamPercentage)/100.0))*dCreditModule/10.0;
                    totalMark+=tempMark;
                    totalCredit+=(dCreditModule/10.0);
//                            clear everything
                    examGrade.getText().clear();
                    cwGrade.getText().clear();
                    examPercentage.getText().clear();
                    creditModule.getText().clear();
//                            set the result TextView into next one
                    counter++;
                    module.setText("Module " + String.valueOf(counter));
                    result.setText("Saved:" + " Module "+ String.valueOf(counter-1));
                }
            }
        });

        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                examGrade = (EditText) findViewById(R.id.editTextGrade1);
                String sExamGrade = examGrade.getText().toString();
                cwGrade = (EditText) findViewById(R.id.editTextCourseWorkGrade1);
                String sCwGrade = cwGrade.getText().toString();
                examPercentage = (EditText) findViewById(R.id.editTextPercentage1);
                String sExamPercentage = examPercentage.getText().toString();
                creditModule = (EditText) findViewById(R.id.editTextModuleCredit1);
                String sCreditModule = creditModule.getText().toString();
                if (sExamGrade.matches("") || sCwGrade.matches("") || sExamPercentage.matches("") || sCreditModule.matches("")) {
                    AlertDialog.Builder x = new AlertDialog.Builder(Gpa.this);

                    x.setTitle("MISSING VALUE");
                    x.setMessage("No EMPTY box is allowed\nPlease check the inputs ^-^");
                    x.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = x.create();
                    alert.show();
                }
                else {
                    double dExamGrade=Double.parseDouble(sExamGrade);
                    double dCwGrade=Double.parseDouble(sCwGrade);
                    double dExamPercentage=Double.parseDouble(sExamPercentage);
                    double dCreditModule = Double.parseDouble(sCreditModule);
                    double tempMark = ((dExamGrade*dExamPercentage/100.0) + (dCwGrade*(100-dExamPercentage)/100.0))*dCreditModule/10.0;
                    totalMark+=tempMark;
                    totalCredit+=(dCreditModule/10.0);
//                            clear everything
                    examGrade.getText().clear();
                    cwGrade.getText().clear();
                    examPercentage.getText().clear();
                    creditModule.getText().clear();
//                            set module name back
                    counter =1;
                    module.setText("Module "+String.valueOf(counter));
//                            calculate the final result
                    double finalResult = totalMark/totalCredit;
                    result.setText(String.valueOf(finalResult));
                    if(finalResult>=70){
                        result.setText("GPA - 4.0 (A)\n FIRST-CLASS HONOURS");
                    }else if(finalResult>=65&&finalResult<=69){
                        result.setText("GPA - 3.7 (B)\n UPPER SECOND-CLASS HONOURS");
                    }else if(finalResult>=60&&finalResult<=64){
                        result.setText("GPA - 3.3 (B)\n UPPER SECOND-CLASS HONOURS");
                    }else if(finalResult>=55&&finalResult<=59){
                        result.setText("GPA - 3 (C)\n LOWER SECOND-CLASS HONOURS");
                    }else if(finalResult>=50&&finalResult<=59){
                        result.setText("GPA - 2.7 (C)\n LOWER SECOND-CLASS HONOURS");
                    }else if(finalResult>=45&&finalResult<=49){
                        result.setText("GPA - 2.3 (D)\n ORDINARY/UNCLASSIFIED");
                    }else{
                        result.setText("FAIL\nREMODULE");
                    }
                    totalCredit=0;
                    totalMark=0;
                }
            }
        });
    }

}

