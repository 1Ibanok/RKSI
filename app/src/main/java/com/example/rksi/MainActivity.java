package com.example.rksi;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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

        //Скрываем профиль после загрузки
        ScrollView pofileScrollView = findViewById(R.id.profile_scroll);
        pofileScrollView.setVisibility(ScrollView.INVISIBLE);

        //Находим размеры дисплея
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Находим окно прокрутки
        ScrollView scrollView = findViewById(R.id.jobs_scroll);

        //Создаём лайаут сетки
        GridLayout lay = new GridLayout(this);

        //Задаём то, что контент сортируется по вертикали
        lay.setOrientation(GridLayout.VERTICAL);

        for (int i = 0; i < 60; i++){

            //Создаём переменную отвечающую за индекс кнопки
            //(почему-то просто из цикла переменные не перевариваются)
            int index = i;

            //Создаём новую кнопку
            ImageButton button_work = new ImageButton(this);

            //Задаём ей изобрадене заднего фона
            button_work.setBackgroundResource(R.drawable.main_botton);

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
            params.setMargins(15, 15, 15, 15);

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

    public void Profile(View view) {
        ScrollView JobsView = findViewById(R.id.jobs_scroll);
        JobsView.setVisibility(ScrollView.INVISIBLE);

        ScrollView ProfileView = findViewById(R.id.profile_scroll);
        ProfileView.setVisibility(ScrollView.VISIBLE);
    }
}