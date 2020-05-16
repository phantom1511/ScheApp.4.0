package com.dastan.scheapp4_0.main.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.dastan.scheapp4_0.R;
import com.dastan.scheapp4_0.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1_000);
    }

    private void selectActivity() {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean isShown = preferences.getBoolean("isShown", false);
        if (!isShown) {
            MainActivity.start(this);
        } else {
            //PreferenceHelper.setIsFirstLaunch();
            //OnBoardActivity.start(this);
        }
        finish();
    }
}
