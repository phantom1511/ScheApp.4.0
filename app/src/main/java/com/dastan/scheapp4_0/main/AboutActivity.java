package com.dastan.scheapp4_0.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dastan.scheapp4_0.R;
import com.google.firebase.auth.FirebaseAuth;

public class AboutActivity extends AppCompatActivity {
    private String name;
    private TextView aboutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        name = preferences.getString("getName", "");

        aboutName = findViewById(R.id.tvAboutUserName);
        aboutName.setText("Dear " + name + " Welcome to Schedule App of INAI.KG");

    }

}
