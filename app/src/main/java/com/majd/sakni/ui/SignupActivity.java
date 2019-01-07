package com.majd.sakni.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.majd.sakni.R;


public class SignupActivity extends AppCompatActivity {

    private EditText phoneEditText;
    public static final String PHONE_KEY="phone";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        phoneEditText = findViewById(R.id.codeEditText);
    }

    public void signUp(View view) {
        String phoneNumber=phoneEditText.getText().toString().trim();
        Intent intent=new Intent(this,VerifyPhoneActivity.class);
        intent.putExtra(PHONE_KEY,phoneNumber);
        startActivity(intent);




    }



}



