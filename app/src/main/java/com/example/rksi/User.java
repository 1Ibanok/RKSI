package com.example.rksi;

import android.util.JsonWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User {
    public String first_name, second_name, email, phone, password, id_job;
    public int coin;
    public User(String firstName, String secondName, String email, String phone, String password) {
        this.first_name = firstName;
        this.second_name = secondName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.coin = 0;
        this.id_job = "";
    }

    public static String Export(User user){
        return new Gson().toJson(user);
    }

    public static User FromJson(String from){
        return new GsonBuilder().create().fromJson(from, User.class);
    }
}
