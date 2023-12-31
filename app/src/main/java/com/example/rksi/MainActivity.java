package com.example.rksi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private int width;
    private int height;
    public static User user;

    private FirebaseDatabase mDatabase;
    private DatabaseReference TiketsBD;
    List<Tiket> list;
    List<Tiket> your_tikets;
    List<String> keys;

    List<String> your_keys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        mDatabase = FirebaseDatabase.getInstance();
        TiketsBD = mDatabase.getReference("TICKETS");
        list = new ArrayList<>();
        keys = new ArrayList<>();
        your_tikets = new ArrayList<>();
        your_keys = new ArrayList<>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = User.FromJson(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                list.clear();
                keys.clear();
                your_tikets.clear();
                your_keys.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getValue().toString().trim() != "") {
                        Tiket tiket;
                        tiket = ds.getValue(Tiket.class);
                        if (tiket != null) {
                            list.add(tiket);
                            keys.add(ds.getKey());
                            if(tiket.isMine(user.email)){
                                your_tikets.add(tiket);
                                your_keys.add(ds.getKey());
                            }
                        }
                    }
                }
                Log.i("sdgkl;gfkgl''", your_tikets.toString());
                Refresh();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        TiketsBD.addValueEventListener(listener);

        //Находим размеры дисплея
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }
    public void DenyTicket(View view){
        DenyTicketFunk();
    }

    public void DenyTicketFunk(){
        user = User.FromJson(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        if(!user.id_job.trim().isEmpty()) {
            TiketsBD.child(user.id_job).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Log.i("ksdfksdjf", user.id_job);
                    if(dataSnapshot.getValue(Tiket.class) != null) {
                        Tiket tiket = dataSnapshot.getValue(Tiket.class);
                        tiket.setDoing_by("");
                        TiketsBD.child(user.id_job).setValue(tiket);

                        user.id_job = "";
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(User.Export(user))
                                .setPhotoUri(null)
                                .build();
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                        OpenJobs();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    user.id_job = "";
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(User.Export(user))
                            .setPhotoUri(null)
                            .build();
                    FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                    OpenJobs();
                }
            });
        }
    }
    public void DeleteTicketFunk(String id){
        if(id != "") {
            TiketsBD.child(id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue(Tiket.class) != null) {
                        TiketsBD.child(id).removeValue();
                        OpenJobs();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }
    @SuppressLint("ResourceAsColor")
    public void Refresh(){

        //Находим окно прокрутки
        ScrollView scrollView = findViewById(R.id.jobs_scroll);
        scrollView.removeAllViews();
        //Создаём лайаут сетки
        GridLayout lay = new GridLayout(this);

        //Задаём то, что контент сортируется по вертикали
        lay.setOrientation(GridLayout.VERTICAL);
        lay.removeAllViews();
        for (int i = 0; i < list.size(); i++){

            Tiket tiket = list.get(i);

            RelativeLayout rlay = new RelativeLayout(this);

            //Создаём переменную отвечающую за индекс кнопки
            //(почему-то просто из цикла переменные не перевариваются)
            int index = i;

            //Создаём новую кнопку
            ImageButton button_work = new ImageButton(this);

            button_work.setBackgroundResource(R.drawable.main_button_job);

            //Задаём ей изобрадене заднего фона
            if (Objects.equals(tiket.doing_by, "")) {
                button_work.setBackgroundResource(R.drawable.main_button_job_active);
            } else if (!Objects.equals(tiket.doing_by, "")) {
                button_work.setBackgroundResource(R.drawable.main_button_job_neactive);
            }


            //Задаём иконке кнопки то, чтобы она была по центру и размер выравнивался по высоте
            button_work.setScaleType(ImageButton.ScaleType.FIT_CENTER);

            //Задаём этой кнопку функци
            button_work.setOnClickListener(v -> {
                int x = index;
                Intent intent = new Intent(v.getContext(), Job.class);
                intent.putExtra("ticket_name", list.get(x).getName());
                intent.putExtra("ticket_description", list.get(x).getDescription());
                intent.putExtra("ticket_id", keys.get(x));
                startActivity(intent);
            });

            //Параметры размера и выравнивания кнопки
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = width - 30;
            params.height = 400;
            params.setMargins(10, 10, 10, 10);

            //Добавляем кнопку в лейаут
            rlay.addView(button_work, params);
            TextView name = new TextView(this);
            TextView contakt = new TextView(this);

            name.setText(tiket.name);
            contakt.setText("Контакт: " + tiket.email_user);

            params.setMargins(20, 0, 5, 5);
            name.setTextSize(35);
            name.setTextColor(R.color.black);


            rlay.addView(name, params);

            lay.addView(rlay);
        }
        //Добавляем лайаут в окно прокрутки
        scrollView.addView(lay);
    }

    public void Jobs(View view) {
        OpenJobs();
    }

    public void OpenJobs(){
        ScrollView ProfileView = findViewById(R.id.profile_scroll);
        ProfileView.setVisibility(ScrollView.INVISIBLE);

        ScrollView JobsView = findViewById(R.id.jobs_scroll);
        JobsView.setVisibility(ScrollView.VISIBLE);

        ScrollView YourScroll = findViewById(R.id.your_scroll);
        YourScroll.setVisibility(ScrollView.INVISIBLE);
    }

    public void create_tiket(View view) {
        Intent intent = new Intent(MainActivity.this, create_tiket.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(MainActivity.this, hello_activity.class);
        startActivity(intent);
        finish();
    }

    public void Profile(View view) {
        UpdateProfile();
    }

    public void UpdateProfile(){
        ScrollView JobsView = findViewById(R.id.jobs_scroll);
        JobsView.setVisibility(ScrollView.INVISIBLE);

        ScrollView scrollView = findViewById(R.id.your_scroll);
        scrollView.setVisibility(ScrollView.INVISIBLE);

        ScrollView ProfileView = findViewById(R.id.profile_scroll);
        ProfileView.setVisibility(ScrollView.VISIBLE);

        TextView NameView = findViewById(R.id.user_name);
        TextView CoinView = findViewById(R.id.user_coin);
        TextView EmailView = findViewById(R.id.user_email);
        TextView PhoneView = findViewById(R.id.user_phone);

        TextView CurrentJob = findViewById(R.id.current_job);
        user = User.FromJson(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        NameView.setText(user.second_name + " " + user.first_name);
        CoinView.setText("Coins: " + user.coin);
        EmailView.setText("Почта: " + user.email);
        PhoneView.setText("Телефон: " + user.phone);

        CurrentJob.setText("Текущее задание:\nОтсутствует");

        if(!user.id_job.isEmpty()){
            TiketsBD.child(user.id_job).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue(Tiket.class) != null){
                        CurrentJob.setText("Текущее задание:\n" + dataSnapshot.getValue(Tiket.class).getName());
                    }
                    else{

                        user.id_job = "";
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(User.Export(user))
                                .setPhotoUri(null)
                                .build();
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                        CurrentJob.setText("Текущее задание:\nОтсутствует");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    CurrentJob.setText("Текущее задание:\nОтсутствует");
                }
            });
        }
    }

    public void LogOut(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        Intent intent = new Intent(MainActivity.this, hello_activity.class);
        startActivity(intent);
        finish();
    }

    public void DeleteAccount(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user_a = auth.getCurrentUser();
        user_a.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(MainActivity.this, hello_activity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    public void ShowTickets() {
        ScrollView ProfileView = findViewById(R.id.profile_scroll);
        ProfileView.setVisibility(ScrollView.INVISIBLE);

        //Находим окно прокрутки
        ScrollView scrollView = findViewById(R.id.your_scroll);
        scrollView.setVisibility(ScrollView.VISIBLE);
        scrollView.removeAllViews();
        //Создаём лайаут сетки
        GridLayout lay = new GridLayout(this);

        //Задаём то, что контент сортируется по вертикали
        lay.setOrientation(GridLayout.VERTICAL);
        lay.removeAllViews();
        Button button = new Button(this);

        button.setOnClickListener(v -> UpdateProfile());
        button.setText("Назад");

        GridLayout.LayoutParams params_b = new GridLayout.LayoutParams();
        params_b.width = width - 300;
        params_b.height = 50;
        params_b.setMargins(10, 10, 10, 10);

        lay.addView(button, params_b);
        for (int i = 0; i < your_tikets.size(); i++){

            Tiket tiket = your_tikets.get(i);

            RelativeLayout rlay = new RelativeLayout(this);

            //Создаём переменную отвечающую за индекс кнопки
            //(почему-то просто из цикла переменные не перевариваются)
            int index = i;

            //Создаём новую кнопку
            ImageButton button_work = new ImageButton(this);


            button_work.setBackgroundResource(R.drawable.main_button_job);

            //Задаём этой кнопку функци
            button_work.setOnClickListener(v -> {
                int x = index;
                DeleteTicketFunk(your_keys.get(x));
            });

            //Параметры размера и выравнивания кнопки
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = width - 30;
            params.height = 400;
            params.setMargins(10, 10, 10, 10);

            //Добавляем кнопку в лейаут
            rlay.addView(button_work, params);
            TextView name = new TextView(this);
            TextView contakt = new TextView(this);

            if(tiket.getDoing_by().isEmpty()) {
                name.setText("Удалить тикет:\n" + tiket.name);
                name.setTextSize(35);
            }
            else {
                name.setText("Подтвердить:\n" + tiket.name + "\nДля:\n" + tiket.getDoing_by());
                name.setTextSize(25);
            }

            contakt.setText("Контакт: " + tiket.email_user);

            name.setTextColor(R.color.black);

            params.setMargins(20, 0, 5, 5);

            rlay.addView(name, params);

            lay.addView(rlay);
        }
        //Добавляем лайаут в окно прокрутки
        scrollView.addView(lay);
    }

    public void ShowYourTickets(View view) {
        ShowTickets();
    }
}