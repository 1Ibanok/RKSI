package com.example.rksi;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class hello_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_activity);

        View decorView = getWindow().getDecorView(); //скрыть панель навигации

        //Скрываем "чёлку"
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);


    }

    public void Login(View view) {
        GridLayout registration = findViewById(R.id.registration);
        registration.setVisibility(GridLayout.INVISIBLE);

        GridLayout login = findViewById(R.id.login);
        login.setVisibility(GridLayout.VISIBLE);
    }

    public void Registration(View view) {
        GridLayout login = findViewById(R.id.login);
        login.setVisibility(GridLayout.INVISIBLE);

        GridLayout registration = findViewById(R.id.registration);
        registration.setVisibility(GridLayout.VISIBLE);
    }

}