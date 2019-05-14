package com.example.schoolapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GpaCalcActivity extends AppCompatActivity {
    public TextView result;         //Located in activity_gpa.xml, as a giant text view used to show result
    public TextView module;         //Located in activity_gpa.xml, shown at the top, representing the current module

    public Button buttonCalc;       //Located at the bottom of activity_gpa.xml: reset all EditText and Module to Module 1
    public Button buttonSave;       //Located before the result box in activity_gpa.xml: total the current value and increase the module counter (Module 1 -> Module 2)

    double totalMark=0;             //Variable to store the total value
    double totalCredit=0;           //Variable to store the Total sum of credits

    public EditText examGrade;      //Input Type: decimals for examMarks
    public EditText cwGrade;        //Input Type: decimals for courseworkMarks
    public EditText examPercentage; //Input Type: decimals for exam percentage (weight)
    public EditText creditModule;   //Input Type: decimals for credit module (in multiples of 10)

    int counter =1;                 //Counter for Module TextView (Module (counter) -> Module (1)) to show module save state



    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_calc);
        result = (TextView) findViewById(R.id.result);          //result: id for TextView with big box
        module = (TextView) findViewById(R.id.textViewModule1); //textViewModule1: top of the page

        buttonSave = (Button) findViewById(R.id.buttonSave);    //initialize "save" button
        buttonCalc = (Button) findViewById(R.id.buttonCalc);    //initialize "calculate" button

        //"save" button function
        //Check if it contains any null value in any of the EditText (input box)
        //If there is any empty field, prompt alert dialog
        //If there is no empty field, perform mark calculation, show user that his marks are saved, and clear all fields
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize all the fields to string for validation purpose (validate whether there is any empty field)
                examGrade = (EditText) findViewById(R.id.editTextGrade1);
                String sExamGrade = examGrade.getText().toString();
                cwGrade = (EditText) findViewById(R.id.editTextCourseWorkGrade1);
                String sCwGrade = cwGrade.getText().toString();
                examPercentage = (EditText) findViewById(R.id.editTextPercentage1);
                String sExamPercentage = examPercentage.getText().toString();
                creditModule = (EditText) findViewById(R.id.editTextModuleCredit1);
                String sCreditModule = creditModule.getText().toString();

                if (sExamGrade.matches("") || sCwGrade.matches("") || sExamPercentage.matches("") || sCreditModule.matches("")) {
                    checkNull(true);
                }
                else {
                    double dExamGrade=Double.parseDouble(sExamGrade);
                    double dCwGrade=Double.parseDouble(sCwGrade);
                    double dExamPercentage=Double.parseDouble(sExamPercentage);
                    double dCreditModule = Double.parseDouble(sCreditModule);

                    double tempMark = calcMark(dExamGrade, dCwGrade, dExamPercentage, dCreditModule); //mark calculation
                    totalMark+=tempMark;                //Sum temporary mark into total mark
                    totalCredit+=(dCreditModule/10.0);  //Sum the credit module into the total module

                    clearEditText();                    //Clear all the fields (set all EditText into "")
                    counter++;                          //Example: Module (counter=1) -> Module (counter=2)
                    module.setText("Module " + String.valueOf(counter));              //Module 1-> Module 2 (Example)
                    result.setText("Saved:" + " Module "+ String.valueOf(counter-1)); //Show user that their progress is saved
                }
            }
        });

        //"Calculate" button function
        //Check if it contains any null value in any of the EditText (input box)
        //If there is any empty field, prompt alert dialog
        //If there is no empty field, perform mark calculation, show user that his final result, reset Module (counter) -> Module 1,and clear all fields
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize all the fields to string for validation purpose (validate whether there is any empty field)
                examGrade = (EditText) findViewById(R.id.editTextGrade1);
                String sExamGrade = examGrade.getText().toString();
                cwGrade = (EditText) findViewById(R.id.editTextCourseWorkGrade1);
                String sCwGrade = cwGrade.getText().toString();
                examPercentage = (EditText) findViewById(R.id.editTextPercentage1);
                String sExamPercentage = examPercentage.getText().toString();
                creditModule = (EditText) findViewById(R.id.editTextModuleCredit1);
                String sCreditModule = creditModule.getText().toString();

                if (sExamGrade.matches("") || sCwGrade.matches("") || sExamPercentage.matches("") || sCreditModule.matches("")) {
                    checkNull(true);
                }
                else {
                    double dExamGrade=Double.parseDouble(sExamGrade);
                    double dCwGrade=Double.parseDouble(sCwGrade);
                    double dExamPercentage=Double.parseDouble(sExamPercentage);
                    double dCreditModule = Double.parseDouble(sCreditModule);

                    double tempMark = calcMark(dExamGrade, dCwGrade, dExamPercentage, dCreditModule); //mark calculation
                    totalMark+=tempMark;                //Sum temporary mark into total mark
                    totalCredit+=(dCreditModule/10.0);  //Sum the credit module into the total module

                    clearEditText();
                    counter++;
                    counter =1;
                    module.setText("Module "+String.valueOf(counter));

                    double finalResult = totalMark/totalCredit; //Final calculation
                    //Reseting all value to 0
                    totalCredit=0;
                    totalMark=0;
                    //Display the final result
                    result.setText(getResult(finalResult));
                }
            }
        });
    }
    //Return grade based on finalResult
    public String getResult(double finalResult){
        String sResult = "";
        if(finalResult>=70){
            sResult = "GPA - 4.0 (A)\n FIRST-CLASS HONOURS";
        }else if(finalResult>=65&&finalResult<=69){
            sResult = "GPA - 3.7 (B)\n UPPER SECOND-CLASS HONOURS";
        }else if(finalResult>=60&&finalResult<=64){
            sResult = "GPA - 3.3 (B)\n UPPER SECOND-CLASS HONOURS";
        }else if(finalResult>=55&&finalResult<=59){
            sResult = "GPA - 3 (C)\n LOWER SECOND-CLASS HONOURS";
        }else if(finalResult>=50&&finalResult<=59){
            sResult = "GPA - 2.7 (C)\n LOWER SECOND-CLASS HONOURS";
        }else if(finalResult>=45&&finalResult<=49){
            sResult = "GPA - 2.3 (D)\n ORDINARY/UNCLASSIFIED";
        }else{
            sResult = "FAIL\nREMODULE";
        }
        return sResult;
    }
    //Null checker
    //If true prompt the alert dialog
    //If false do nothing
    public void checkNull(boolean nullInput){
        if (nullInput == true){
            AlertDialog.Builder x = new AlertDialog.Builder(GpaCalcActivity.this);

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
    }

    //Mark calculation algorithm
    public double calcMark(double examGrade, double cwGrade, double examPercentage, double creditModule){
        double calculatedMark = ((examGrade*examPercentage/100.0) + (cwGrade*(100-examPercentage)/100.0))*creditModule/10.0;
        return calculatedMark;
    }

    //Clear(Reset) every field
    public void clearEditText(){
        examGrade.getText().clear();
        cwGrade.getText().clear();
        examPercentage.getText().clear();
        creditModule.getText().clear();
    }
}


