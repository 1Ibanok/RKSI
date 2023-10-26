package com.example.rksi;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class hello_activity extends AppCompatActivity {

    private String USER_KEY = "User";
    public DatabaseReference UsersBD;


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

    public void GetAllUsers(View view){
        UsersBD = FirebaseDatabase.getInstance("https://rksi-2e196-default-rtdb.europe-west1.firebasedatabase.app/").getReference(USER_KEY);
        ArrayList<User> Users = new ArrayList<>();
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    Users.add(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Все плохо
            }

        };
    }

    public void Reg(View view){
        EditText First_name = findViewById(R.id.first_name);
        EditText Second_name = findViewById(R.id.second_name);
        EditText Email = findViewById(R.id.email);
        EditText Phone = findViewById(R.id.phone);
        EditText Password = findViewById(R.id.password);

        UsersBD = FirebaseDatabase.getInstance("https://rksi-2e196-default-rtdb.europe-west1.firebasedatabase.app/").getReference(USER_KEY);

        String first_name = First_name.getText().toString();
        String second_name = Second_name.getText().toString();
        String email = Email.getText().toString();
        String phone = Phone.getText().toString();
        String password = Password.getText().toString();
        User newUser = new User(first_name, second_name, email, phone, password);

        UsersBD.push().setValue(newUser);
        UsersBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Сделать уведомлние что все хорошо

                GridLayout registration = findViewById(R.id.registration);
                registration.setVisibility(GridLayout.INVISIBLE);

                GridLayout login = findViewById(R.id.login);
                login.setVisibility(GridLayout.VISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Сделать уведомлние что все плохо
            }
        });
    }

    public void Log(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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

}