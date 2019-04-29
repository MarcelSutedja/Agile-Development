package com.assignment.mrlau.adapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends Activity {

    private Spinner course_Drop_Down;
    private Spinner FPTime_Drop_Down;
    private Spinner sex_Drop_Down;
    private EditText inputEmail, inputPassword;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth auth;
    public String diploma_Course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        sex_Drop_Down = (Spinner)findViewById(R.id.spinner_Sex);
        FPTime_Drop_Down = (Spinner)findViewById(R.id.spinner_Time);
        course_Drop_Down = (Spinner) findViewById(R.id.spinner_Course);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Configure and display Drop Down Menu for Diploma Course Selection In Registration
        ArrayAdapter<String> course_Adaptor = new ArrayAdapter<String>(SignupActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.courses));
        course_Adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_Drop_Down.setAdapter(course_Adaptor);

        //Configure and display Drop Down Menu for Full Time or Part Time
        ArrayAdapter<String> time_Adaptor = new ArrayAdapter<String>(SignupActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.time));
        time_Adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FPTime_Drop_Down.setAdapter(time_Adaptor);
        //Configure and display Drop Down Menu for Full Time or Part Time
        ArrayAdapter<String> sex_Adaptor = new ArrayAdapter<String>(SignupActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sex));
        sex_Adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_Drop_Down.setAdapter(sex_Adaptor);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter your Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "Welcome! Thank you for registering", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    showProgressDialog();
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    diploma_Course = course_Drop_Down.getSelectedItem().toString();
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
        this. finish();
    }
}
