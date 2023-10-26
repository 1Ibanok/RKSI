package com.example.rksi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class hello_activity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
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

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user!=null && user.isEmailVerified()){
            LogIn();
        }
    }

    public void Reg(View view){
        EditText First_name = findViewById(R.id.first_name);
        EditText Second_name = findViewById(R.id.second_name);
        EditText Email = findViewById(R.id.email);
        EditText Phone = findViewById(R.id.phone);
        EditText Password = findViewById(R.id.password);

        String email = Email.getText().toString();
        String password = Password.getText().toString();

        User user_parse = new User(First_name.getText().toString(),
                Second_name.getText().toString(),
                email, Phone.getText().toString(), password);

        String user_data = User.Export(user_parse);

        TextView error_view = findViewById(R.id.registration_failed_text);

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                user = auth.getCurrentUser();
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(user_data)
                                .setPhotoUri(null)
                                .build();
                        user.updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                LogIn();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                user.delete();
                                error_view.setText("Ошибка: " + e);
                            }

                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        user.delete();
                        error_view.setText("Ошибка: " + e);
                    }
                });
            }
        });
    }

    public void Log(View view){
        EditText Email = findViewById(R.id.email_login);
        EditText Password = findViewById(R.id.password_login);

        String email = Email.getText().toString();
        String password = Password.getText().toString();

        TextView error_view = findViewById(R.id.login_failed_text);
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(auth.getCurrentUser().isEmailVerified()) {
                LogIn();
            }
        }).addOnFailureListener(e -> {
            error_view.setText("Ошибка: " + e);
        });
    }

    public void toLogin(View view) {
        GridLayout registration = findViewById(R.id.registration);
        registration.setVisibility(GridLayout.INVISIBLE);

        GridLayout login = findViewById(R.id.login);
        login.setVisibility(GridLayout.VISIBLE);
    }

    public void toRegistration(View view) {
        GridLayout login = findViewById(R.id.login);
        login.setVisibility(GridLayout.INVISIBLE);

        GridLayout registration = findViewById(R.id.registration);
        registration.setVisibility(GridLayout.VISIBLE);
    }

    public void LogIn(){
        user = auth.getCurrentUser();
        Intent intent = new Intent(hello_activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}