package com.example.rksi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView(); //скрыть панель навигации
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

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        RelativeLayout layout = findViewById(R.id.rectangle_1);

        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
        GridLayout lay = new GridLayout(this);
        lay.setOrientation(GridLayout.VERTICAL);
        for (int i = 0; i < 60; i++){
            ImageButton button_work = new ImageButton(this);
            button_work.setBackgroundResource(R.drawable.main_botton);
            button_work.setScaleType(ImageButton.ScaleType.FIT_CENTER);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = width - 30;
            params.height = 400;
            params.setMargins(15, 15, 15, 15);

            lay.addView(button_work, params);
        }
        scrollView.addView(lay);
    }

    public void NextActivity(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}