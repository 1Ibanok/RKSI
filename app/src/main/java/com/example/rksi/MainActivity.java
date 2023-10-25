package com.example.rksi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 50; i++){
            TextView view = new TextView(this);
            view.setText("test" + i);
            view.setTextSize(15f);
            lay.addView(view, 300, 100);
        }
        scrollView.addView(lay);
    }
}