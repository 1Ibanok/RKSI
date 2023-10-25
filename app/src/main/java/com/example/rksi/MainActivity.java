package com.example.rksi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);

        for (int i = 0; i<10; i++) {
            TextView textView = new TextView(this);
            textView.setText(i);
            textView.setTextSize(26);
            scrollView.addView(textView);
        }
        /* TextView text = (TextView)findViewById(R.id.text);

        String str = new String();

        for (int i = 0; i<100; i++){
            str = str + "1";
        }
        text.setText(str);*/

    }
}