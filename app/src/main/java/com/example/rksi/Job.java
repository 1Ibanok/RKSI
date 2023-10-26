package com.example.rksi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Job extends Activity {
    private String id;

    public static User user;
    FirebaseUser user_d;

    private FirebaseDatabase mDatabase;
    private DatabaseReference TiketsBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job);

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

        Bundle arguments = getIntent().getExtras();
        TextView name_view = findViewById(R.id.tik_name);
        TextView desc_view = findViewById(R.id.tik_desc);
        name_view.setText(arguments.get("ticket_name").toString());
        desc_view.setText(arguments.get("ticket_description").toString());
        id = arguments.get("ticket_id").toString();

        user_d = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        TiketsBD = mDatabase.getReference("TICKETS");
    }

    public void onClickUndoActivity(View view) {
        finish();
    }
    public void AcceptTiket(View view) {
        user = User.FromJson(user_d.getDisplayName());
        Log.i("asfafmflnklfdkl", "||||" + user.id_job);
        Log.i("asfafmflnklfdkl", "||||ID" + id);
        if(id != user.id_job && user.id_job.trim().isEmpty()) {
            TiketsBD.child(id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue(Tiket.class) != null) {
                        Tiket tiket = dataSnapshot.getValue(Tiket.class);
                        tiket.setDoing_by(user.email);
                        TiketsBD.child(id).setValue(tiket);

                        user.id_job = id;
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(User.Export(user))
                                .setPhotoUri(null)
                                .build();
                        user_d.updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }
}