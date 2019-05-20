package com.example.schoolapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.schoolapp.Adaptor.ModuleDataList;
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

                moduleDataList.clear();

                for(DataSnapshot moduleDataSnapshot: dataSnapshot.getChildren()){
                    ModuleData moduleData = moduleDataSnapshot.getValue(ModuleData.class);

                    moduleDataList.add(moduleData);
                }
                ModuleDataList listAdapter = new ModuleDataList(ModuleDataHistoryList.this,moduleDataList);
                listViewModuleData.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
