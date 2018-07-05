package com.example.luiz1.nocash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IntroActivity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIMEOUT);



     }
}



