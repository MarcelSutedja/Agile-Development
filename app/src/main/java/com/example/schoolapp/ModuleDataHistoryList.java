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

    ListView listViewModuleData;
    List<ModuleData> moduleDataList;
    DatabaseReference databaseModuleData;



    FirebaseAuth auth;
    FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_data_history);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        listViewModuleData = (ListView) findViewById(R.id.listViewModuleDataList);
        databaseModuleData = FirebaseDatabase.getInstance().getReference("ModuleData");

        moduleDataList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseModuleData.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double total =0;
                double credit=0;
                moduleDataList.clear();

                for(DataSnapshot moduleDataSnapshot: dataSnapshot.getChildren()){
                    ModuleData moduleData = moduleDataSnapshot.getValue(ModuleData.class);
                    total+=moduleData.getFinalMark();
                    credit+=moduleData.getCreditModule()/10.0;
                    moduleDataList.add(moduleData);
                }
                ModuleDataList listAdapter = new ModuleDataList(ModuleDataHistoryList.this,moduleDataList);
                listViewModuleData.setAdapter(listAdapter);
                double finalMark = total/credit;
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
