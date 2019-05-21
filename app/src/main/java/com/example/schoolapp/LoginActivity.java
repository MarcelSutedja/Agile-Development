package com.example.schoolapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText email, password;
    Button btn_Login;
    private ProgressDialog mProgressDialogLogin;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_Login = findViewById(R.id.btn_Login);
        //Once the Login button is pressed
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();          //Collect Email that is inputted by user
                String txt_password = password.getText().toString();   //Collect Password that is inputted by user
                //If any fields are empty, show message that says "all fields are required" at the center of the screen
                if(TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)) { //Set up what happens when nothing is inputted to email and password
                    Toast emptyFields = Toast.makeText(LoginActivity.this,"All fields are required", Toast.LENGTH_LONG);
                    emptyFields.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 1250);
                    emptyFields.show();
                    //If password is less than 6 characters, show message that says "Password must contain at least 6 characters" at the center of the screen
                }
                else{
                    auth.signInWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){               //If login authentication is successful
                                //showProgressDialogLogin();
                                Intent loginIntent = new Intent (LoginActivity.this, MainActivity.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(loginIntent);         //Go to Main Activity
                                finish();
                            }else{
                                Toast failedAuth = Toast.makeText(LoginActivity.this,"Failed Authentication", Toast.LENGTH_LONG);
                                failedAuth.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 1250);
                                failedAuth.show();
                            }
                        }
                    });
                }
            }
        });

    }
    private void showProgressDialogLogin() {
        if (mProgressDialogLogin == null) {
            mProgressDialogLogin = new ProgressDialog(this);
            mProgressDialogLogin.setMessage(getString(R.string.loading));
            mProgressDialogLogin.setIndeterminate(true);
            mProgressDialogLogin.dismiss();
        }

        mProgressDialogLogin.show();
    }
}
