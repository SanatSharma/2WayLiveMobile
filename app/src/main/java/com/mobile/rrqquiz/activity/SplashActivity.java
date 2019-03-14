package com.mobile.rrqquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mobile.rrqquiz.R;
import com.mobile.rrqquiz.utility.SharedPrefManager;

import androidx.appcompat.app.AppCompatActivity;

import static com.mobile.rrqquiz.utility.SharedPrefManager.KEY_PIN;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                String pin = SharedPrefManager.getInstance(SplashActivity.this).getUserData(KEY_PIN);
                Intent intent;
                if (pin != null && !pin.isEmpty()) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);

                } else {
                    intent = new Intent(SplashActivity.this, RegistrationActivity.class);

                }
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
