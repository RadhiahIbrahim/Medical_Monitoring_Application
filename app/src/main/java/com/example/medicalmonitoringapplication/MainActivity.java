package com.example.medicalmonitoringapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openRegister(View v){
        Intent i = new Intent(this,SignUp.class);
        startActivity(i);
    }
    public void openLogin(View v){
        Intent i = new Intent(this,Login.class);
        startActivity(i);
    }
}