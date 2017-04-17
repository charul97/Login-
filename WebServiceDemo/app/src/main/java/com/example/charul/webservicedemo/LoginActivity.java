package com.example.charul.webservicedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private EditText email;
    private EditText password;
    private TextView signup;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressdialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.register);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signup = (TextView) findViewById(R.id.signin);

        button.setOnClickListener(this);
        signup.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
            //enter profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }
    }

    private void userlogin(){
        String em= email.getText().toString().trim();
        String pass= password.getText().toString().trim();
        if(TextUtils.isEmpty(em)){
            //email is empty
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(pass)){
            //password is empty
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        //if both validations are ok
        //we will first show a progressdialog
        progressdialog.setMessage("Registering user..");
        progressdialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressdialog.dismiss();
                if(task.isSuccessful()){
                    //start profile activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==button){
            userlogin();
        }
        else if(view==signup){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
