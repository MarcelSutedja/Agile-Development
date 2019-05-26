package com.example.schoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.schoolapp.Adaptor.ModuleDataList;
import com.example.schoolapp.Extra.ModuleData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ModuleDataHistoryList extends AppCompatActivity {

    //List variables for ModuleData object class
    ListView listViewModuleData;
    List<ModuleData> moduleDataList;

    //Database reference to retrieve data from the firebase database
    DatabaseReference databaseModuleData;

    //Firebase variables
    FirebaseAuth auth;
    FirebaseUser firebaseUser;      //To retrieve current user id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_data_history);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();                         //Get current user id
        listViewModuleData = (ListView) findViewById(R.id.listViewModuleDataList);
        databaseModuleData = FirebaseDatabase.getInstance().getReference("ModuleData");//get all data parked under Module Data branch in Firebase json tree
        moduleDataList = new ArrayList<>();                                                 //Variable to store all the Module Data instances
    }

    @Override
    protected void onStart() {
        super.onStart();

        //access the module data for current user
        //If data change, update the list view
        databaseModuleData.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double total =0;
                double credit=0;
                moduleDataList.clear();

                for(DataSnapshot moduleDataSnapshot: dataSnapshot.getChildren()){
                    ModuleData moduleData = moduleDataSnapshot.getValue(ModuleData.class); //Snapshot of module data (eg. PADS, OSD)
                    total+=moduleData.getFinalMark();                                      //Get the mark and add it into total mark
                    credit+=moduleData.getCreditModule()/10.0;                             //Add the credit
                    moduleDataList.add(moduleData);                                        //Add the current module into moduleDataList for view
                }
                ModuleDataList listAdapter = new ModuleDataList(ModuleDataHistoryList.this,moduleDataList);
                listViewModuleData.setAdapter(listAdapter);                                 //show it in the ListView
                double finalMark = total/credit;                                            //Calculate the total mark
                TextView textViewMark = (TextView) findViewById(R.id.textViewModuleDataMark);
                textViewMark.setText("TOTAL MARK   : "+String.format("%.2f",finalMark));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public double calcMark(double examGrade, double cwGrade, double examPercentage, double creditModule){
        double calculatedMark = ((examGrade*examPercentage/100.0) + (cwGrade*(100-examPercentage)/100.0))*creditModule/10.0;
        return calculatedMark;
    }
}
