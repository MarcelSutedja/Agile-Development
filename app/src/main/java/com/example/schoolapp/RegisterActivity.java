package com.example.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolapp.Extra.GlobalVar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText name, email, password;
    Button register_Btn;
    Spinner courseDropDown;
    TextView textView;

    FirebaseAuth auth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textView = (TextView)findViewById(R.id.course);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        email = findViewById(R.id.r_email);
        password = findViewById(R.id.r_password);
        register_Btn = findViewById(R.id.btn_Register);

        auth = FirebaseAuth.getInstance();
        courseDropDown = (Spinner) findViewById(R.id.spinner_major);
        //Configure and display Drop Down Menu for Diploma Course Selection In Registration
        ArrayAdapter<String> course_Adaptor = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.courses));
        course_Adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseDropDown.setAdapter(course_Adaptor);
        courseDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = courseDropDown.getSelectedItem().toString();
                GlobalVar.setData(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


                register_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txt_name = name.getText().toString();
                        String txt_email = email.getText().toString();
                        String txt_password = password.getText().toString();
                        //If any fields are empty, show message that says "all fields are required" at the center of the screen
                        if (TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                            Toast emptyFields = Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_LONG);
                            emptyFields.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 1250);
                            emptyFields.show();
                            //If password is less than 6 characters, show message that says "Password must contain at least 6 characters" at the center of the screen
                        } else if (txt_password.length() < 6) {
                            Toast shortPassword = Toast.makeText(RegisterActivity.this, "Password must contain at least 6 characters", Toast.LENGTH_LONG);
                            shortPassword.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 1250);
                            shortPassword.show();
                        } else {
                            register(txt_name, txt_email, txt_password, GlobalVar.getData());
                        }
                    }
                });
    }
    private void register(final String name, String email, String password, final String timetable){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser!=null;
                    String userId = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("name",name);
                    hashMap.put("imageURL", "default");
                    hashMap.put("currentStatus","Offline");
                    hashMap.put("search",name.toLowerCase());
                    hashMap.put("timetable", timetable);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }
            }
        });
    }
}
