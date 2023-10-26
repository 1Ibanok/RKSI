package com.example.rksi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class create_tiket extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_tiket);

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

    public void SendTiket(View view){
        DatabaseReference TiketsBD = FirebaseDatabase.getInstance("https://rksi-2e196-default-rtdb.europe-west1.firebasedatabase.app/").getReference("TIKETS");

        EditText Name_tiket = findViewById(R.id.name);
        EditText Describtion_tiket = findViewById(R.id.describtion);

        String name_tiket = Name_tiket.getText().toString();
        String describtion_tiket = Describtion_tiket.getText().toString();

        Tiket tiket = new Tiket(name_tiket, describtion_tiket,"EMAIL");

        TiketsBD.push().setValue(tiket);

        TiketsBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Сделать уведомлние что все хорошо
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Сделать уведомлние что все плохо
            }
        });
    }

    public void onClickUndoActivity(View view) {
        finish();
    }
}