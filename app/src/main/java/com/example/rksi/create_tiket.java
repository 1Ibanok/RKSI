package com.example.rksi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
        DatabaseReference TiketsBD = FirebaseDatabase.getInstance().getReference("TICKETS");

        EditText Name_tiket = findViewById(R.id.name);
        EditText Describtion_tiket = findViewById(R.id.describtion);

        String name_tiket = Name_tiket.getText().toString();
        String describtion_tiket = Describtion_tiket.getText().toString();

        User user = User.FromJson(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        String email_user = user.email;

        Tiket tiket = new Tiket();
        tiket.setName(name_tiket);
        tiket.setDescription(describtion_tiket);
        tiket.setEmail_user(email_user);
        tiket.setDoing_by("");

        TiketsBD.push().setValue(tiket).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                finish();
            }
        });
    }

    public void onClickUndoActivity(View view) {
        finish();
    }
}