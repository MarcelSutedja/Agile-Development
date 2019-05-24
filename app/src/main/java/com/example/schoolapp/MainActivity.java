package com.example.schoolapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.schoolapp.Body.User;
import com.example.schoolapp.Extra.GlobalVar;
import com.example.schoolapp.Fragments.ChatFragment;
import com.example.schoolapp.Fragments.ProfileFragment;
import com.example.schoolapp.Fragments.UserFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView profile_Image;
    TextView name;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        profile_Image = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);                      //Store database information as an object
                GlobalVar.setData(user.getModule());
                GlobalVar.setMajor(user.getTimetable());
                name.setText(user.getName());
                if (user.getImageURL().equals("default")){
                    profile_Image.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_Image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TabLayout tabLayout = findViewById(R.id.tab_Layout);
        ViewPager viewPager = findViewById(R.id.view_Pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Create New Fragments
        viewPagerAdapter.addFragment(new ChatFragment(),"Chats");
        viewPagerAdapter.addFragment(new UserFragment(),"Users");
        viewPagerAdapter.addFragment(new ProfileFragment(),"Profile");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
    //Initialise Menu(...) at the top right corner
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the dotdotdot; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dotdotdot, menu);
        return true;
    }

    @Override
    //Configure what happens when you press the items under ... at the top right
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:{
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Confirm Logout").setCancelable(true)
                        // (true) dialog can be ignored by pressing the back button
                        // Confirm Logout
                        .setPositiveButton("Logout",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(MainActivity.this, StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        finish();
                                    }
                                })
                        // Misclick
                        .setNegativeButton("Back",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }).show();
        }//End of Logout
            return true;
            case R.id.gpa_calc:{
                startActivity(new Intent(MainActivity.this, GpaCalcActivity.class));
            }
            return true;
            case R.id.time_table:{
                startActivity(new Intent(MainActivity.this, TimeTableActivity.class));
            }
            return true;
            case R.id.module_data:{
                startActivity(new Intent(MainActivity.this, ModuleDataHistoryList.class));
            }
            return true;
        }
        return false;
    }
    class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment>fragments;
        private ArrayList<String>titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }
        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);

        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int i){
            return titles.get(i);
        }
    }//End of ViewPageAdaptor Class
    private void status (String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Status", status);

        reference.updateChildren(hashMap);
    }
    @Override
    protected void onResume(){
        super.onResume();
        status("Online");
    }
    @Override
    protected void onPause(){
        super.onPause();
        status("Offline");
    }
}//End of MainActivity Class
