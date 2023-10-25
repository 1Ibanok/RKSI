package com.example.rksi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Job extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job);


    }

    public void onClickUndoActivity(View view) {
        finish();
    }
}