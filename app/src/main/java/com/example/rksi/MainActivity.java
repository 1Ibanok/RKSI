package com.example.rksi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int width;
    private int height;
    User user;
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
        SharedPreferences sharedPref = this.getPreferences(this.MODE_PRIVATE);
        user = User.FromJson(sharedPref.getString("user_data", ""));

        //Находим размеры дисплея
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        Refresh();

    }

    public void Refresh(){
        //Находим окно прокрутки
        ScrollView scrollView = findViewById(R.id.jobs_scroll);

        //Создаём лайаут сетки
        GridLayout lay = new GridLayout(this);

        //Задаём то, что контент сортируется по вертикали
        lay.setOrientation(GridLayout.VERTICAL);
        lay.removeAllViews();
        for (int i = 0; i < 60; i++){

            //Создаём переменную отвечающую за индекс кнопки
            //(почему-то просто из цикла переменные не перевариваются)
            int index = i;

            //Создаём новую кнопку
            ImageButton button_work = new ImageButton(this);

            //Задаём ей изобрадене заднего фона
            button_work.setBackgroundResource(R.drawable.botton_work);

            //Задаём иконке кнопки то, чтобы она была по центру и размер выравнивался по высоте
            button_work.setScaleType(ImageButton.ScaleType.FIT_CENTER);

            //Задаём этой кнопку функци
            button_work.setOnClickListener(v -> {
                int x = index;
                Intent intent = new Intent(v.getContext(), Job.class);
                startActivity(intent);
            });

            //Параметры размера и выравнивания кнопки
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = width - 30;
            params.height = 400;
            params.setMargins(10, 10, 10, 10);

            //Добавляем кнопку в лейаут
            lay.addView(button_work, params);
        }
        //Добавляем лайаут в окно прокрутки
        scrollView.addView(lay);
    }

    public void Refresh(View view){
        //Находим окно прокрутки
        ScrollView scrollView = findViewById(R.id.jobs_scroll);

        //Создаём лайаут сетки
        GridLayout lay = new GridLayout(this);

        //Задаём то, что контент сортируется по вертикали
        lay.setOrientation(GridLayout.VERTICAL);
        lay.removeAllViews();
        for (int i = 0; i < 60; i++){

            //Создаём переменную отвечающую за индекс кнопки
            //(почему-то просто из цикла переменные не перевариваются)
            int index = i;

            //Создаём новую кнопк
            ImageButton button_work = new ImageButton(this);

            //Задаём ей изобрадене заднего фона
            button_work.setBackgroundResource(R.drawable.botton_work);

            //Задаём иконке кнопки то, чтобы она была по центру и размер выравнивался по высоте
            button_work.setScaleType(ImageButton.ScaleType.FIT_CENTER);

            //Задаём этой кнопку функци
            button_work.setOnClickListener(v -> {
                int x = index;
                Intent intent = new Intent(v.getContext(), Job.class);
                startActivity(intent);
            });

            //Параметры размера и выравнивания кнопки
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = width - 30;
            params.height = 400;
            params.setMargins(10, 10, 10, 10);

            //Добавляем кнопку в лейаут
            lay.addView(button_work, params);
        }
        //Добавляем лайаут в окно прокрутки
        scrollView.addView(lay);
    }



    public void Jobs(View view) {
        ScrollView ProfileView = findViewById(R.id.profile_scroll);
        ProfileView.setVisibility(ScrollView.INVISIBLE);
        ScrollView JobsView = findViewById(R.id.jobs_scroll);
        JobsView.setVisibility(ScrollView.VISIBLE);
    }

    public void create_tiket(View view) {
        Intent intent = new Intent(MainActivity.this, create_tiket.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(MainActivity.this, hello_activity.class);
        startActivity(intent);
    }

    public void Profile(View view) {
        ScrollView JobsView = findViewById(R.id.jobs_scroll);
        JobsView.setVisibility(ScrollView.INVISIBLE);

        ScrollView ProfileView = findViewById(R.id.profile_scroll);
        ProfileView.setVisibility(ScrollView.VISIBLE);

        TextView NameView = findViewById(R.id.user_name);
        TextView EmailView = findViewById(R.id.user_email);
        TextView PhoneView = findViewById(R.id.user_phone);

        NameView.setText(user.second_name + " " + user.first_name);
        EmailView.setText(user.email);
        PhoneView.setText(user.phone);
    }
}